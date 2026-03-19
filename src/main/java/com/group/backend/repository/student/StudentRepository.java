package com.group.backend.repository.student;

import com.group.backend.entity.Student;
import java.util.Optional;

public interface StudentRepository {
  Student save(Student student);
  Optional<Student> findByUserId(Integer userId);
}
