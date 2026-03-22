package com.group.backend.repository.admin;

import com.group.backend.entity.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Integer>
{
    @Query("SELECT a FROM Attempt a JOIN FETCH a.exam WHERE a.user.id = :userId")
    List<Attempt> findByUserIdWithExam(@Param("userId") Integer userId);
}
