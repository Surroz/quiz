package org.surro.questionservice.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.surro.questionservice.model.QuestionAnswer;
import org.surro.questionservice.model.QuestionResponse;
import org.surro.questionservice.model.persistance.Question;
import org.surro.questionservice.repository.QuestionRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuestionService {

    @Autowired
    QuestionRepo questionRepo;

    public List<Question> getAllQuestions() {
        return questionRepo.findAll();
    }

    public List<Question> getQuestionsByTopic(String topic) {
        return questionRepo.findByTopic(topic);
    }

    public List<Integer> getQuestionIdsByTopic(String topic, int quantity) {
        return questionRepo.findRandomQuestionsByTopic(topic, quantity);
    }

    public String addQuestion(Question question) {
        questionRepo.save(question);
        return "success";
    }

    public List<QuestionResponse> getQuestionsById(List<Integer> ids) {
        List<Question> questions = questionRepo.findAllById(ids);
        List<QuestionResponse> userQuestions = new ArrayList<>();
        for (Question question : questions) {
            QuestionResponse questionResponse = new QuestionResponse(
                    question.getId(),
                    question.getQuestion(),
                    question.getFirstVariant(),
                    question.getSecondVariant(),
                    question.getThirdVariant(),
                    question.getFourthVariant());
            userQuestions.add(questionResponse);
        }
        return userQuestions;
    }

    public Integer checkAnswers(List<QuestionAnswer> answers) {
        List<Integer> questionNumbers = new ArrayList<>();
        for (QuestionAnswer answer : answers) {
            questionNumbers.add(answer.getQuestionNumber());
        }
        AtomicInteger score = new AtomicInteger();
        List<Question> questions = questionRepo.findAllById(questionNumbers);
        questions.forEach(question -> {
            answers.stream()
                    .filter(answer -> answer.getQuestionNumber().equals(question.getId()))
                    .findFirst()
                    .ifPresent(answer -> {
                        if (answer.getResponse().equals(question.getCorrectAnswer())) {
                            score.getAndIncrement();
                        }
                    });
        });
        return score.get();
    }
}
