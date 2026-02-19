package org.surro.quizservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionAnswer {
    private Integer questionNumber;
    private String response;
}
