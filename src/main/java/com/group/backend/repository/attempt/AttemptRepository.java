package com.group.backend.repository.attempt;

import com.group.backend.entity.Attempt;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.group.backend.entity.enums.AttemptStatus;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Integer> {

    List<Attempt> findAllByUser_IdOrderByStartTimeDesc(Integer userId);
    long countByStatus(AttemptStatus status);
}