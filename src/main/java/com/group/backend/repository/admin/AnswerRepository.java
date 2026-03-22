package com.group.backend.repository.admin;

import com.group.backend.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer>
{
    @Query("""
        SELECT a FROM Answer a
        JOIN FETCH a.question q
        JOIN FETCH a.selectedOption so
        JOIN FETCH q.options opts
        WHERE a.attempt.id = :attemptId
    """)
    List<Answer> findByAttemptIdWithDetails(@Param("attemptId") Integer attemptId);
}
