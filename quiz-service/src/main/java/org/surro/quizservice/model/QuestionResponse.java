package org.surro.quizservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionResponse {
    private Integer id;
    private String question;
    private String firstVariant;
    private String secondVariant;
    private String thirdVariant;
    private String fourthVariant;
}
