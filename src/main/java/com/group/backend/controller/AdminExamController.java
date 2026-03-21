package com.group.backend.controller;

import java.util.*;

import com.group.backend.constant.entity.QuestionEntityConstant;
import com.group.backend.dto.*;
import com.group.backend.service.admin.AdminExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/exams")
@RequiredArgsConstructor
public class AdminExamController {
    private final AdminExamService adminExamService;

    //GET /admin/exams
    @GetMapping
    public ResponseEntity<ResponseGeneral<List<AdminExamResponse>>> getAllExams()
    {
        return ResponseEntity.ok(
                ResponseGeneral.ofSuccess(
                        "Get exams successfully",
                        adminExamService.getAllExams()
                )
        );
    }

    //GET /admin/exams/{examId}
    @GetMapping("/{examId}")
    public ResponseEntity<ResponseGeneral<AdminExamResponse>> getExamById(
            @PathVariable int examId
    ) {
        return ResponseEntity.ok(
                ResponseGeneral.ofSuccess(
                        "Get exam successfully",
                        adminExamService.getExamById(examId)
                )
        );
    }

    //POST /admin/exams
    @PostMapping
    public ResponseEntity<ResponseGeneral<AdminExamResponse>> createExam(
            @Valid @RequestBody AdminExamRequest request
    ) {
        AdminExamResponse adminExamResponse = adminExamService.createExam(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseGeneral.ofSuccess(
                        "Create exam successfully",
                        adminExamResponse
                )
        );
    }

    //PUT /admin/exams/{examId}
    @PutMapping("/{examId}")
    public ResponseEntity<ResponseGeneral<AdminExamResponse>> editExam(
        @PathVariable int examId,
        @Valid @RequestBody AdminExamRequest request
    )   {
        AdminExamResponse response = adminExamService.editExam(examId, request);

        return ResponseEntity.ok(
                ResponseGeneral.ofSuccess(
                        "Update exam successfully",
                        response
                )
        );
    }

    //DELETE /admin/exams/{examId}
    @DeleteMapping("/{examId}")
    public ResponseEntity<ResponseGeneral<Void>> deleteExam(
        @PathVariable int examId
    ) {
        adminExamService.deleteExam(examId);

        return ResponseEntity.ok(
                ResponseGeneral.ofSuccess(
                        "Delete exam successfully",
                        null
                )
        );
    }

    //POST /admin/exams/{examId}/questions
    @PostMapping("/{examId}/questions")
    public ResponseEntity<ResponseGeneral<Void>> createQuestion(
            @PathVariable int examId,
            @Valid @RequestBody AdminQuestionRequest request
    ) {
        adminExamService.createQuestion(examId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseGeneral.ofSuccess("Create question successfully", null)
        );
    }

    //DELETE /admin/exams/{examId}/questions/{questionId}
    @DeleteMapping("/{examId}/questions/{questionId}")
    public ResponseEntity<ResponseGeneral<Void>> deleteQuestion(
            @PathVariable int examId,
            @PathVariable int questionId
    ) {
        adminExamService.deleteQuestion(examId, questionId);

        return ResponseEntity.ok(
                ResponseGeneral.ofSuccess("Delete question successfully", null)
        );
    }

    //POST /admin/exams/{examId}/questions/import
    @PostMapping("/{examId}/questions/import")
    public ResponseEntity<ResponseGeneral<Void>> importQuestions(
            @PathVariable int examId,
            @Valid @RequestBody AdminQuestionImportRequest request
    ) {
        adminExamService.importQuestions(examId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseGeneral.ofSuccess("Import questions successfully", null)
        );
    }
}
