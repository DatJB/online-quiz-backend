package com.group.backend.controller;

import com.group.backend.dto.ExamDTO;
import com.group.backend.dto.QuestionDTO;
import com.group.backend.service.StudentExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student/exams")
@RequiredArgsConstructor
public class StudentExamController
{
    @Autowired
    private StudentExamService studentExamService;

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
}
