package org.surro.quizservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.surro.quizservice.model.QuestionAnswer;
import org.surro.quizservice.model.QuestionResponse;
import org.surro.quizservice.model.QuizParams;
import org.surro.quizservice.service.QuizService;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizService service;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizParams quizParams){
        return ResponseEntity.ok(service.createQuiz(quizParams));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionResponse>> getQuizQuestions(@PathVariable Integer id){
        return ResponseEntity.ok(service.getQuizQuestions(id));
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<QuestionAnswer> responses){
        return ResponseEntity.ok(service.getAmountOfRightAnswers(id, responses));
    }
}
