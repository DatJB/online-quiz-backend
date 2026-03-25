package com.group.backend.repository.student;

import com.group.backend.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentExamRepository extends JpaRepository<Exam, Integer>
{
    @Query("SELECT COUNT(e) FROM Exam e WHERE MONTH(e.createdAt) = :month and YEAR(e.createdAt) = :year")
    Long countExamByMonthAndYear(@Param("month") int month, @Param("year") int year);
}