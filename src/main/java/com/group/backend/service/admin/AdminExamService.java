package com.group.backend.service.admin;

import com.group.backend.dto.AdminExamRequest;
import com.group.backend.dto.AdminExamResponse;
import com.group.backend.dto.AdminQuestionRequest;
import com.group.backend.dto.AdminQuestionImportRequest;
import java.util.*;
public interface AdminExamService {
    List<AdminExamResponse> getAllExams();
    AdminExamResponse createExam(AdminExamRequest request);
    AdminExamResponse getExamById(int examId);
    AdminExamResponse editExam(int examId, AdminExamRequest request);
    void deleteExam(int examId);
    void createQuestion(int examId, AdminQuestionRequest request);
    void deleteQuestion(int examId, int questionId);
    void importQuestions(int examId, AdminQuestionImportRequest request);
}
