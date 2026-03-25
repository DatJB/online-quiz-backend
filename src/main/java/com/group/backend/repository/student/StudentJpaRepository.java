package com.group.backend.repository.student;

import com.group.backend.entity.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentJpaRepository extends JpaRepository<Student, Integer> {

  Optional<Student> findByUserId(Integer userId);
}
