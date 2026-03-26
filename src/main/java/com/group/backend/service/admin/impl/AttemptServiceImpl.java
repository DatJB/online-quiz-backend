package com.group.backend.service.admin.impl;

import com.group.backend.dto.*;
import com.group.backend.entity.*;
import com.group.backend.repository.admin.AnswerRepository;
import com.group.backend.repository.admin.StudentRepository;
import com.group.backend.repository.attempt.AttemptRepository;
import com.group.backend.service.admin.AttemptService;
import com.group.backend.service.admin.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttemptServiceImpl implements AttemptService
{
    private final AttemptRepository attemptRepository;
    private final AnswerRepository answerRepository;
    private final StudentRepository studentRepository;
    private final ExcelService excelService;

    @Override
    public List<AttemptDTO> getResultsByStudentId(Integer studentId)
    {
        List<Attempt> attempts = attemptRepository.findByUserIdWithExam(studentId);

        return attempts.stream().map(this::toDTO).toList();
    }

    @Override
    public AttemptDetailDTO getAttemptDetailById(Integer attemptId)
    {
        List<Answer> answers = answerRepository.findByAttemptIdWithDetails(attemptId);

        List<AdminQuestionResultDTO> results = answers.stream()
                .map(this::toQuestionResultDTO)
                .toList();

        return AttemptDetailDTO.builder()
                .attemptId(attemptId)
                .questionResults(results)
                .build();
    }

    @Override
    public byte[] exportResults(Integer studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Attempt> attempts = attemptRepository.findByUserIdWithExam(studentId);
        List<AttemptDTO> attemptDTOs = attempts.stream().map(this::toDTO).toList();

        Double averageScore =  attemptDTOs.stream()
                .mapToDouble(AttemptDTO::getScore)
                .average()
                .orElse(0.0f);

        StudentReportDTO studentReportDTO = StudentReportDTO.builder()
                    .studentId(studentId)
                    .studentCode(student.getStudentCode())
                    .email(student.getUser().getEmail())
                    .studentName(student.getUser().getUsername())
                    .averageScore(averageScore)
                    .attempts(attemptDTOs)
                    .build();

        return excelService.exportResultsToExcel(studentReportDTO);
    }

    private AttemptDTO toDTO(Attempt attempt)
    {
        Long timeSpent = null;
        if (attempt.getStartTime() != null && attempt.getEndTime() != null)
        {
            timeSpent = Duration.between(attempt.getStartTime(), attempt.getEndTime()).toMinutes();
        }

        return AttemptDTO.builder()
                .id(attempt.getId())
                .examTitle(attempt.getExam().getTitle())
                .startTime(attempt.getStartTime())
                .endTime(attempt.getEndTime())
                .timeSpent(timeSpent)
                .score(attempt.getScore())
                .totalQuestions(attempt.getTotalQuestions())
                .correctAnswer(attempt.getCorrectAnswers())
                .correctRate(100f * attempt.getCorrectAnswers() / attempt.getTotalQuestions())
                .status(attempt.getStatus().toString())
                .build();
    }

    private AdminQuestionResultDTO toQuestionResultDTO(Answer answer)
    {
        Question question = answer.getQuestion();

        Option correctOption = question.getOptions().stream()
                .filter(Option::getIsCorrect)
                .findFirst()
                .orElse(null);

        return AdminQuestionResultDTO.builder()
                .answerId(answer.getId())
                .questionContent(question.getContent())
                .questionExplanation(question.getExplanation())
                .correctOption(toOptionDTO(correctOption))
                .selectedOption(toOptionDTO(answer.getSelectedOption()))
                .isCorrect(answer.getIsCorrect())
                .build();
    }

    private OptionDTO toOptionDTO(Option option)
    {
        return OptionDTO.builder()
                .id(option.getId())
                .content(option.getContent())
                .build();
    }
}
