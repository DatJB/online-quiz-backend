package com.group.backend.controller;

import com.group.backend.dto.*;
import com.group.backend.service.admin.AttemptService;
import com.group.backend.service.admin.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/students")
@RequiredArgsConstructor
public class StudentController
{
    private final StudentService studentService;
    private final AttemptService attemptService;

    @GetMapping
    public ResponseEntity<Page<StudentDTO>> getAllStudents(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(
                studentService.getAllStudents(pageNumber, pageSize, keyword)
        );
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> getStudentById(
            @PathVariable Integer studentId
    ) {
        return ResponseEntity.ok(
                studentService.getStudentById(studentId)
        );
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(
            @RequestBody CreateStudentRequest request
    ) {
        return ResponseEntity.ok(
                studentService.createStudent(request)
        );
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable Integer studentId,
            @RequestBody UpdateStudentRequest request
    ) {
        return ResponseEntity.ok(
                studentService.updateStudent(studentId, request)
        );
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<StudentDTO> deleteStudent(
            @PathVariable Integer studentId
    ) {
        studentService.deleteStudent(studentId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{studentId}/results")
    public ResponseEntity<List<AttemptDTO>> getStudentResults(
            @PathVariable Integer studentId
    ) {
        return ResponseEntity.ok(
                attemptService.getResultsByStudentId(studentId)
        );
    }

    @GetMapping("/{studentId}/results/export")
    public ResponseEntity<byte[]> exportStudentResult(@PathVariable Integer studentId)
    {
        byte[] fileData = attemptService.exportResults(studentId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=student_report.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileData);
    }
}
