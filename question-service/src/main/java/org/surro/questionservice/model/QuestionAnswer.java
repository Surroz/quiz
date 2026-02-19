package org.surro.questionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionAnswer {
    private Integer questionNumber;
    private String response;
}
