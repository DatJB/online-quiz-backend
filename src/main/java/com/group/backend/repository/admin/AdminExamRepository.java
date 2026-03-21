package com.group.backend.repository.admin;

import com.group.backend.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AdminExamRepository extends JpaRepository<Exam, Integer> {
}
