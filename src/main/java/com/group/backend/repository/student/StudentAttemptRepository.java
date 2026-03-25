package com.group.backend.repository.student;

import com.group.backend.entity.Attempt;
import com.group.backend.entity.enums.AttemptStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentAttemptRepository extends JpaRepository<Attempt, Integer>
{
    Optional<Attempt> findByExamIdAndUserIdAndStatus(
            Integer examId,
            Integer userId,
            AttemptStatus status
    );

    @Query("SELECT COUNT(a) FROM Attempt a WHERE MONTH(a.startTime) = :month and YEAR(a.startTime) = :year")
    Long countAttemptByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT ROUND(avg(a.score), 1) FROM Attempt a WHERE MONTH(a.endTime) = :month AND YEAR(a.endTime) = :year AND a.status <> com.group.backend.entity.enums.AttemptStatus.IN_PROGRESS ")
    Double avgPointByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("""
        SELECT a FROM Attempt a
        JOIN FETCH a.user
        JOIN FETCH a.exam
        WHERE a.status <> com.group.backend.entity.enums.AttemptStatus.IN_PROGRESS
        ORDER BY a.endTime DESC
        LIMIT 10
        """)
    List<Attempt> findRecentAttempts();
}
