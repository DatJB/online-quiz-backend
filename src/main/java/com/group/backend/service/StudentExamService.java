package com.group.backend.service;

import com.group.backend.dto.ExamDTO;
import com.group.backend.dto.QuestionDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentExamService
{
    Page<ExamDTO> getAllExams(int page, int size, String sortBy);

    List<QuestionDTO> getQuestionsByExamId(Integer examId);
}
