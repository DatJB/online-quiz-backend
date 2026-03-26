package com.group.backend.service.admin;

import com.group.backend.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
public interface AdminExamService {
    List<AdminExamResponse> getAllExams();
    AdminExamResponse createExam(AdminExamRequest request);
    AdminExamResponse getExamById(int examId);
    AdminExamResponse editExam(int examId, AdminExamRequest request);
    void deleteExam(int examId);
    void createQuestion(int examId, AdminQuestionRequest request);
    void editQuestion(int examId, int questionId, AdminQuestionRequest request);
    void deleteQuestion(int examId, int questionId);
    void importQuestions(int examId, MultipartFile file);
    void editOption(int examId, int questionId, int optionId, AdminOptionRequest request);
    List<AdminQuestionResponse> getExamQuestions(int examId);
}
