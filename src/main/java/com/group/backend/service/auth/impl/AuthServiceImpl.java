package com.group.backend.service.auth.impl;

import com.group.backend.dto.LoginRequest;
import com.group.backend.dto.LoginResponse;
import com.group.backend.dto.RegisterRequest;
import com.group.backend.entity.Student;
import com.group.backend.entity.User;
import com.group.backend.entity.enums.Role;
import com.group.backend.exception.business.InvalidEmailException;
import com.group.backend.exception.business.InvalidStudentCodeException;
import com.group.backend.exception.business.InvalidUsernameException;
import com.group.backend.repository.student.StudentRepository;
import com.group.backend.repository.user.UserRepository;
import com.group.backend.security.JwtService;
import com.group.backend.service.auth.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final StudentRepository studentRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  @Override
  @Transactional
  public void register(RegisterRequest registerRequest) {
    if (userRepository.existsByUsername(registerRequest.getUsername())) {
      throw new InvalidUsernameException();
    }

    if (userRepository.existsByEmail(registerRequest.getEmail())) {
      throw new InvalidEmailException();
    }

    if (userRepository.existsByStudent_StudentCode(registerRequest.getStudentCode())) {
      throw new InvalidStudentCodeException();
    }

    User newUser = User.builder()
        .username(registerRequest.getUsername())
        .email(registerRequest.getEmail())
        .password(passwordEncoder.encode(registerRequest.getPassword()))
        .role(Role.STUDENT)
        .build();
    User savedUser = userRepository.save(newUser);

    Student newStudent = Student.builder()
        .user(savedUser)
        .studentCode(registerRequest.getStudentCode())
        .className(registerRequest.getClassName())
        .phone(registerRequest.getPhone())
        .build();
    studentRepository.save(newStudent);

  }

  @Override
  public LoginResponse login(LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
            loginRequest.getPassword()));

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    String jwtToken = jwtService.generateToken(userDetails);

    return LoginResponse.builder()
        .token(jwtToken)
        .build();
  }
}
