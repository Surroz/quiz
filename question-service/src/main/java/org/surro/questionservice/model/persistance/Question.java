package org.surro.questionservice.model.persistance;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String question;
    private String firstVariant;
    private String secondVariant;
    private String thirdVariant;
    private String fourthVariant;
    private String correctAnswer;
    private String difficulty;
    private String topic;
}
