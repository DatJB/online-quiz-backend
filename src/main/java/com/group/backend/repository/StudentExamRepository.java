package com.group.backend.repository;

import com.group.backend.entity.Exam;
import com.group.backend.entity.Option;
import com.group.backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentExamRepository extends JpaRepository<Exam, Integer>
{
    @Query("SELECT COUNT(e) FROM Exam e WHERE MONTH(e.createdAt) = :month and YEAR(e.createdAt) = :year")
    Long countExamByMonthAndYear(@Param("month") int month, @Param("year") int year);
}