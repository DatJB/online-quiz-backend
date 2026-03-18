package com.group.backend.service.impl;

import com.group.backend.dto.ExamDTO;
import com.group.backend.dto.OptionDTO;
import com.group.backend.dto.QuestionDTO;
import com.group.backend.entity.Exam;
import com.group.backend.entity.Question;
import com.group.backend.repository.StudentExamRepository;
import com.group.backend.repository.StudentQuestionRepository;
import com.group.backend.service.StudentExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentExamServiceImpl implements StudentExamService
{
    @Autowired
    private StudentExamRepository studentExamRepository;
    @Autowired
    private StudentQuestionRepository studentQuestionRepository;

    @Override
    public Page<ExamDTO> getAllExams(int page, int size, String sortBy)
    {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return studentExamRepository.findAll(pageable).map(this::toDTO);
    }

    @Override
    public List<QuestionDTO> getQuestionsByExamId(Integer examId)
    {
        if (!studentExamRepository.existsById(examId))
        {
            throw new RuntimeException("Exam not found");
        }

        return studentQuestionRepository.findByExamId(examId).stream()
                .map(this::toQuestionDTO).collect(Collectors.toList());
    }

    private ExamDTO toDTO(Exam exam)
    {
        return ExamDTO.builder()
                .id(exam.getId())
                .title(exam.getTitle())
                .description(exam.getDescription())
                .type(String.valueOf(exam.getType()))
                .startTime(exam.getStartTime())
                .endTime(exam.getEndTime())
                .duration(exam.getDuration())
                .build();
    }

    private QuestionDTO toQuestionDTO(Question question)
    {
        List<OptionDTO> options = question.getOptions()
                .stream()
                .map(opt -> OptionDTO.builder()
                        .id(opt.getId())
                        .content(opt.getContent())
                        .build())
                .toList();

        return QuestionDTO.builder()
                .id(question.getId())
                .content(question.getContent())
                .explanation(question.getExplanation())
                .options(options)
                .build();
    }
}
