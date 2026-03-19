package com.group.backend.repository;

import com.group.backend.entity.Attempt;
import com.group.backend.entity.enums.AttemptStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentAttemptRepository extends JpaRepository<Attempt, Integer>
{
    Optional<Attempt> findByExamIdAndUserIdAndStatus(
            Integer examId,
            Integer userId,
            AttemptStatus status
    );
}
