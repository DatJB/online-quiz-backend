package com.group.backend.repository.attempt;

import com.group.backend.entity.Attempt;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.group.backend.entity.enums.AttemptStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Integer> {

    List<Attempt> findAllByUser_IdOrderByStartTimeDesc(Integer userId);

    long countByStatus(AttemptStatus status);

    @Query("SELECT a FROM Attempt a JOIN FETCH a.exam WHERE a.user.id = :userId")
    List<Attempt> findByUserIdWithExam(@Param("userId") Integer userId);

    @Query("""
        SELECT COUNT(a) FROM Attempt a
        WHERE MONTH(a.startTime) = :month
        AND YEAR(a.startTime) = :year
    """)
    long countAttemptsByMonth(@Param("month") int month, @Param("year") int year);

    @Query("""
        SELECT ROUND(AVG(a.score),1)
        FROM Attempt a
        WHERE MONTH(a.endTime) = :month
        AND YEAR(a.endTime) = :year
        AND a.status <> 'IN_PROGRESS'
    """)
    Double avgScoreByMonth(@Param("month") int month, @Param("year") int year);

    @Query("""
        SELECT 
            CASE 
                WHEN a.score >= 8 THEN '8-10'
                WHEN a.score >= 6 THEN '6-8'
                WHEN a.score >= 4 THEN '4-6'
                ELSE '0-4'
            END,
            COUNT(a)
        FROM Attempt a
        WHERE a.status <> 'IN_PROGRESS'
        GROUP BY 1
    """)
    List<Object[]> scoreDistribution();

    @Query("""
        SELECT COUNT(DISTINCT a.user.id)
        FROM Attempt a
    """)
    long countDistinctStudents();

    @Query("""
        SELECT MONTH(a.endTime), AVG(a.score)
        FROM Attempt a
        WHERE a.status <> 'IN_PROGRESS'
        GROUP BY MONTH(a.endTime)
        ORDER BY MONTH(a.endTime)
    """)
    List<Object[]> scoreTrend();

    @Query("""
        SELECT e.id, e.title, COUNT(DISTINCT a.user.id)
        FROM Attempt a
        JOIN a.exam e
        WHERE a.status <> 'IN_PROGRESS'
        GROUP BY e.id, e.title
        ORDER BY COUNT(DISTINCT a.user.id) DESC
    """)
    List<Object[]> countStudentsByExam();
}
