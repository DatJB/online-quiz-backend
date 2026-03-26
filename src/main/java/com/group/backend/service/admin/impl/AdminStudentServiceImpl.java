package com.group.backend.service.admin.impl;

import com.group.backend.dto.CreateStudentRequest;
import com.group.backend.dto.StudentDTO;
import com.group.backend.dto.UpdateStudentRequest;
import com.group.backend.entity.Student;
import com.group.backend.entity.User;
import com.group.backend.entity.enums.Role;
import com.group.backend.repository.UserRepository;
import com.group.backend.repository.admin.StudentRepository;
import com.group.backend.repository.attempt.AttemptRepository;
import com.group.backend.service.admin.AdminStudentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminStudentServiceImpl implements AdminStudentService
{
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final AttemptRepository attemptRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<StudentDTO> getAllStudents(int pageNumber, int pageSize, String keyword)
    {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Student> studentPage;

        if (keyword != null && !keyword.trim().isEmpty())
        {
            studentPage = studentRepository.findByKeyword(keyword, pageable);
        }
        else
        {
            studentPage = studentRepository.findAll(pageable);
        }

        return studentPage.map(this::toDTO);
    }

    @Override
    public StudentDTO getStudentById(Integer studentId)
    {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return toDTO(student);
    }

    @Override
    @Transactional
    public StudentDTO createStudent(CreateStudentRequest request)
    {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .build();

        user = userRepository.save(user);

        Student student = Student.builder()
                .user(user)
                .studentCode(request.getStudentCode())
                .className(request.getClassName())
                .build();

        student = studentRepository.save(student);

        return toDTO(student);
    }

    @Override
    @Transactional
    public StudentDTO updateStudent(Integer studentId, UpdateStudentRequest request)
    {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (request.getStudentCode() != null)
        {
            student.setStudentCode(request.getStudentCode());
        }

        if (request.getClassName() != null)
        {
            student.setClassName(request.getClassName());
        }

        User user = student.getUser();
        if (user != null)
        {
            if (request.getUsername() != null)
            {
                user.setUsername(request.getUsername());
            }

            if (request.getPassword() != null)
            {
                user.setPassword(request.getPassword());
            }
        }

        studentRepository.save(student);
        return toDTO(student);
    }

    @Override
    @Transactional
    public void deleteStudent(Integer studentId)
    {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        User user = student.getUser();

        studentRepository.delete(student);
        if (user != null)
        {
            userRepository.delete(user);
        }
    }

    private StudentDTO toDTO(Student student)
    {
        User user = student.getUser();
        return StudentDTO.builder()
                .userId(student.getUserId())
                .studentCode(student.getStudentCode())
                .username(user != null ? user.getUsername() : null)
                .email(user != null ? user.getEmail() : null)
                .password(user != null ? user.getPassword() : null)
                .className(student.getClassName())
                .createdAt(user != null ? user.getCreatedAt() : null)
                .build();
    }
}
