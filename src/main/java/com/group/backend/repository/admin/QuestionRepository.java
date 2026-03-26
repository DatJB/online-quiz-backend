package com.group.backend.repository.admin;

import com.group.backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query("SELECT DISTINCT q FROM Question q JOIN FETCH q.options WHERE q.exam.id = :examId")
    List<Question> findByExamId(@Param("examId") int examId);
}
