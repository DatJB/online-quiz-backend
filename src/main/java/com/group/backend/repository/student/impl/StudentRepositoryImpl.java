package com.group.backend.repository.student.impl;

import com.group.backend.entity.Student;
import com.group.backend.repository.student.StudentJpaRepository;
import com.group.backend.repository.student.StudentRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

  private final StudentJpaRepository studentJpaRepository;

  public StudentRepositoryImpl(StudentJpaRepository studentJpaRepository) {
    this.studentJpaRepository = studentJpaRepository;
  }

  @Override
  public Student save(Student student) {
    studentJpaRepository.save(student);
    return student;
  }

  @Override
  public Optional<Student> findByUserId(Integer userId) {
    return studentJpaRepository.findByUserId(userId);
  }
}
