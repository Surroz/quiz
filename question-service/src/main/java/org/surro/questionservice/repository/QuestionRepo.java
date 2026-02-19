package org.surro.questionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.surro.questionservice.model.persistance.Question;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {
    List<Question> findByTopic(String topic);

    @Query(value = "SELECT q.id FROM question q Where q.topic=:topic ORDER BY RANDOM() LIMIT :quantity", nativeQuery = true)
    List<Integer> findRandomQuestionsByTopic(String topic, int quantity);
}
