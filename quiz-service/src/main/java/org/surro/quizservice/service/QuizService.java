package org.surro.quizservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.surro.quizservice.feign.QuestionClient;
import org.surro.quizservice.model.QuestionAnswer;
import org.surro.quizservice.model.QuestionResponse;
import org.surro.quizservice.model.QuizParams;
import org.surro.quizservice.model.persistance.Quiz;
import org.surro.quizservice.repository.QuizRepo;

import java.util.List;

@Service
public class QuizService {
    @Autowired
    QuizRepo quizRepo;
    @Autowired
    QuestionClient questionClient;

    public String createQuiz(QuizParams quizParams) {
        List<Integer> ids = questionClient.getQuestionIds(quizParams.topic(),quizParams.quantity()).getBody();
        Quiz quiz = new Quiz();
        quiz.setName(quizParams.title());
        quiz.setQuestionIds(ids);
        quizRepo.save(quiz);
        return "Success";
    }

    public List<QuestionResponse> getQuizQuestions(Integer id) {
        List<Integer> questionIds = quizRepo.findById(id).orElseThrow().getQuestionIds();
        return questionClient.getQuestionsForQuiz(questionIds).getBody();
    }

    public Integer getAmountOfRightAnswers(Integer id, List<QuestionAnswer> answers) {
        return    questionClient.checkAnswers(answers).getBody();
    }

}
