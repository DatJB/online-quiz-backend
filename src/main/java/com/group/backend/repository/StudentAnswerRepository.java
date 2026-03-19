package com.group.backend.repository;

import com.group.backend.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentAnswerRepository extends JpaRepository<Answer, Integer>
{
}
