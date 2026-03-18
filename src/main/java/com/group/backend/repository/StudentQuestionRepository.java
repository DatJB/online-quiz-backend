package com.group.backend.repository;

import com.group.backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentQuestionRepository extends JpaRepository<Question, Integer>
{
    @Query("SELECT q FROM Question q LEFT JOIN FETCH q.options WHERE q.exam.id = :examId")
    List<Question> findByExamId(Integer examId);
}
