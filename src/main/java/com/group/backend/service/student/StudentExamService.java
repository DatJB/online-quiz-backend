package com.group.backend.service.student;

import com.group.backend.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentExamService
{
    Page<ExamDTO> getAllExams(int page, int size, String sortBy);

    List<QuestionDTO> getQuestionsByExamId(Integer examId);

    StartAttemptDTO startAttempt(Integer examId, Integer userId);

    ExamResultDTO submitAttempt(Integer examId, SubmitAttemptRequestDTO request, Integer userId);
}
