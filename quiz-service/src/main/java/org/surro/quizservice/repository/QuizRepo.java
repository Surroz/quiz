package org.surro.quizservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.surro.quizservice.model.persistance.Quiz;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Integer> {
}
