package org.surro.questionservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.surro.questionservice.model.QuestionAnswer;
import org.surro.questionservice.model.QuestionResponse;
import org.surro.questionservice.model.persistance.Question;
import org.surro.questionservice.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService service;

    @GetMapping("/all-questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(service.getAllQuestions());
    }

    @GetMapping("/topic/{topic}")
    public ResponseEntity<List<Question>> getAllQuestionsByTopic(@RequestParam String topic) {
        return ResponseEntity.ok(service.getQuestionsByTopic(topic));

    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(service.addQuestion(question));
    }

    @PostMapping("/get-by-ids")
    public ResponseEntity<List<QuestionResponse>> getQuestionsForQuiz(@RequestParam List<Integer> ids) {
        List<QuestionResponse> questions = service.getQuestionsById(ids);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/get-ids")
    public ResponseEntity<List<Integer>> getQuestionIds(@RequestParam String topic, @RequestParam int quantity) {
        List<Integer> ids = service.getQuestionIdsByTopic(topic, quantity);
        return ResponseEntity.ok(ids);
    }

    @PostMapping("/check-answers")
    public ResponseEntity<Integer>  checkAnswers(@RequestBody List<QuestionAnswer> answers) {
        int score = service.checkAnswers(answers);
        return ResponseEntity.ok(score);
    }

}
