package com.group.backend.repository.admin;

import com.group.backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
