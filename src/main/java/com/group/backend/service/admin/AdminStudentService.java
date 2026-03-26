package com.group.backend.service.admin;

import com.group.backend.dto.CreateStudentRequest;
import com.group.backend.dto.StudentDTO;
import com.group.backend.dto.UpdateStudentRequest;
import org.springframework.data.domain.Page;

public interface AdminStudentService
{
    Page<StudentDTO> getAllStudents(int pageNumber, int pageSize, String keyword);

    StudentDTO getStudentById(Integer studentId);

    StudentDTO createStudent(CreateStudentRequest request);

    StudentDTO updateStudent(Integer studentId, UpdateStudentRequest request);

    void deleteStudent(Integer studentId);
}
