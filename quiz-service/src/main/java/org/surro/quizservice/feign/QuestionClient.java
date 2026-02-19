package org.surro.quizservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.surro.quizservice.model.QuestionAnswer;
import org.surro.quizservice.model.QuestionResponse;

import java.util.List;

@FeignClient(name = "QUESTION-SERVICE", path = "/question")
public interface QuestionClient {
    @PostMapping("/get-by-ids")
    public ResponseEntity<List<QuestionResponse>> getQuestionsForQuiz(@RequestParam List<Integer> ids);

    @GetMapping("/get-ids")
    public ResponseEntity<List<Integer>> getQuestionIds(@RequestParam String topic, @RequestParam int quantity);

    @PostMapping("/check-answers")
    public ResponseEntity<Integer>  checkAnswers(@RequestBody List<QuestionAnswer> answers);

}
