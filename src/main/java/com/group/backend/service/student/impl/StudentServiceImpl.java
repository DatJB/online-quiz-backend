package com.group.backend.service.student.impl;

import com.group.backend.dto.PasswordUpdateRequest;
import com.group.backend.dto.StudentProfileResponse;
import com.group.backend.entity.Attempt;
import com.group.backend.entity.Student;
import com.group.backend.entity.User;
import com.group.backend.exception.business.InvalidPasswordException;
import com.group.backend.exception.business.ResourceNotFoundException;
import com.group.backend.repository.attempt.AttemptRepository;
import com.group.backend.repository.student.StudentRepository;
import com.group.backend.repository.user.UserRepository;
import com.group.backend.security.CustomUserDetails;
import com.group.backend.service.student.StudentService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;
  private final UserRepository userRepository;
  private final AttemptRepository attemptRepository;
  private final PasswordEncoder passwordEncoder;

  private User getCurrentUser() {
    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return userDetails.getUser();
  }

  @Override
  public StudentProfileResponse getMyProfile() {
    User user = getCurrentUser();
    Student student = studentRepository.findByUserId(user.getId())
        .orElseThrow(() -> new ResourceNotFoundException());

    return StudentProfileResponse.builder()
        .username(user.getUsername())
        .email(user.getEmail())
        .studentCode(student.getStudentCode())
        .className(student.getClassName())
        .phone(student.getPhone())
        .build();
  }

  @Override
  public List<Attempt> getMyResults() {
    return attemptRepository.findAllByUserIdOrderByStartTimeDesc(getCurrentUser().getId());
  }

  @Override
  @Transactional
  public void changePassword(PasswordUpdateRequest request) {
    User user = getCurrentUser();

    if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
      throw new InvalidPasswordException();
    }

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user);
  }
}