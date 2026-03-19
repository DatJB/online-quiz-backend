package com.group.backend.service.impl;

import com.group.backend.dto.*;
import com.group.backend.entity.*;
import com.group.backend.entity.enums.AttemptStatus;
import com.group.backend.entity.enums.ExamType;
import com.group.backend.repository.*;
import com.group.backend.service.StudentExamService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    @Autowired
    private StudentAttemptRepository studentAttemptRepository;
    @Autowired
    private StudentAnswerRepository studentAnswerRepository;
    @Autowired
    private StudentOptionRepository studentOptionRepository;

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

    @Override
    public StartAttemptDTO startAttempt(Integer examId, Integer userId)
    {
        Exam exam = studentExamRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        // Check exam scheduled
        if (exam.getType() == ExamType.SCHEDULED)
        {
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(exam.getStartTime()) || now.isAfter(exam.getEndTime()))
            {
                throw new RuntimeException("Exam not available");
            }
        }

        Attempt attempt = Attempt.builder()
                .exam(exam)
                .user(User.builder().id(userId).build())
                .startTime(LocalDateTime.now())
                .status(AttemptStatus.IN_PROGRESS)
                .totalQuestions(studentQuestionRepository.countByExamId(examId))
                .build();

        Attempt saved = studentAttemptRepository.save(attempt);

        return StartAttemptDTO.builder()
                .id(saved.getId())
                .examId(examId)
                .startTime(saved.getStartTime())
                .status(saved.getStatus().name())
                .build();
    }

    @Override
    @Transactional
    public ExamResultDTO submitAttempt(Integer examId, SubmitAttemptRequestDTO request, Integer userId)
    {
        System.out.println(examId + " " + userId);
        Attempt attempt = studentAttemptRepository.findByExamIdAndUserIdAndStatus(examId, userId, AttemptStatus.IN_PROGRESS)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        // Check timeout
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = attempt.getStartTime().plusMinutes(attempt.getExam().getDuration());
        AttemptStatus finalStatus = now.isAfter(deadline) ? AttemptStatus.TIMEOUT : AttemptStatus.COMPLETED;
        long duration = ChronoUnit.MINUTES.between(attempt.getStartTime(), now);

        // Score and save questions, answers
        int correctCount = 0;
        List<Answer> answers = new ArrayList<>();
        List<QuestionResultDTO> questionResults = new ArrayList<>();

        for (AnswerRequestDTO answerRequest : request.getAnswers())
        {
            boolean isCorrect = false;

            if (answerRequest.getSelectedOptionId() != null)
            {
                Option option = studentOptionRepository.findById(answerRequest.getSelectedOptionId())
                        .orElseThrow(() -> new RuntimeException("Option not found"));
                isCorrect = option.getIsCorrect();
            }

            if (isCorrect) correctCount++;

            answers.add(Answer.builder()
                    .attempt(attempt)
                    .question(Question.builder().id(answerRequest.getQuestionId()).build())
                    .selectedOption(answerRequest.getSelectedOptionId() != null
                            ? Option.builder().id(answerRequest.getSelectedOptionId()).build()
                            : null)
                    .isCorrect(isCorrect)
                    .build());

            Question question = studentQuestionRepository.findById(answerRequest.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            List<OptionResultDTO> optionResults = question.getOptions()
                    .stream()
                    .map(opt -> OptionResultDTO.builder()
                            .id(opt.getId())
                            .content(opt.getContent())
                            .isCorrect(opt.getIsCorrect())
                            .build())
                    .toList();

            questionResults.add(QuestionResultDTO.builder()
                    .questionId(question.getId())
                    .content(question.getContent())
                    .explanation(question.getExplanation())
                    .options(optionResults)
                    .selectedOptionId(answerRequest.getSelectedOptionId())
                    .isCorrect(isCorrect)
                    .build());
        }

        studentAnswerRepository.saveAll(answers);

        int totalQuestions = attempt.getTotalQuestions();
        float score = totalQuestions > 0 ? (((float) correctCount / totalQuestions) * 10) : 0;

        attempt.setStatus(finalStatus);
        attempt.setEndTime(now);
        attempt.setCorrectAnswers(correctCount);
        attempt.setScore(score);
        studentAttemptRepository.save(attempt);

        return ExamResultDTO.builder()
                .attemptId(attempt.getId())
                .score(score)
                .totalQuestions(totalQuestions)
                .correctAnswers(correctCount)
                .status(finalStatus.name())
                .startTime(attempt.getStartTime())
                .endTime(now)
                .duration(duration)
                .questions(questionResults)
                .build();
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
