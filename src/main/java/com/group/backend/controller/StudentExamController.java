package com.group.backend.controller;

import com.group.backend.dto.*;
import com.group.backend.security.CustomUserDetails;
import com.group.backend.service.student.StudentExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student/exams")
@RequiredArgsConstructor
public class StudentExamController
{
    private final StudentExamService studentExamService;

    @GetMapping
    public ResponseEntity<Page<ExamDTO>> getAllExams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        Page<ExamDTO> exams = studentExamService.getAllExams(page, size, sortBy);
        return ResponseEntity.ok(exams);
    }

    @GetMapping("/{examId}/questions")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByExamId(@PathVariable Integer examId)
    {
        List<QuestionDTO> questions = studentExamService.getQuestionsByExamId(examId);
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/{examId}/start")
    public ResponseEntity<StartAttemptDTO> startAttempt(@PathVariable Integer examId)
    {
        Integer userId = getCurrentUserId();
        StartAttemptDTO attempt = studentExamService.startAttempt(examId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(attempt);
    }

    @PostMapping("/{examId}/submit")
    public ResponseEntity<ExamResultDTO> submitAttempt (
            @PathVariable Integer examId,
            @RequestBody SubmitAttemptRequestDTO request
    ) {
        Integer userId = getCurrentUserId();
        ExamResultDTO result = studentExamService.submitAttempt(examId, request, userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{examId}/results")
    public ResponseEntity<ResponseGeneral<ExamResultDTO>> getExamResult(@PathVariable Integer examId) {
        Integer userId = getCurrentUserId();
        return ResponseEntity.ok(
                ResponseGeneral.ofSuccess(
                        "Get result successfully",
                        studentExamService.getExamResult(examId, userId)
                )
        );
    }

    // Get UserID from JWT
    private Integer getCurrentUserId() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userDetails.getUser().getId();
    }
}
