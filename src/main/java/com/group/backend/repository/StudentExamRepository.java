package com.group.backend.repository;

import com.group.backend.entity.Exam;
import com.group.backend.entity.Option;
import com.group.backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentExamRepository extends JpaRepository<Exam, Integer>
{
}