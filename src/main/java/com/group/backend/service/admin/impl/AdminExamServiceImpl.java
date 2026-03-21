package com.group.backend.service.admin.impl;

import java.util.*;
import com.group.backend.dto.AdminExamRequest;
import com.group.backend.dto.AdminExamResponse;
import com.group.backend.entity.Exam;
import com.group.backend.exception.base.BusinessException;
import com.group.backend.exception.business.BadRequestException;
import com.group.backend.repository.admin.QuestionRepository;
import com.group.backend.service.admin.AdminExamService;
import com.group.backend.repository.admin.AdminExamRepository;
import com.group.backend.exception.business.ResourceNotFoundException;
import com.group.backend.dto.AdminQuestionRequest;
import com.group.backend.dto.AdminQuestionImportRequest;
import com.group.backend.entity.Option;
import com.group.backend.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AdminExamServiceImpl implements AdminExamService {
    private final AdminExamRepository adminExamRepository;
    private final QuestionRepository questionRepository;

    @Override
    public List<AdminExamResponse> getAllExams()
    {
        return adminExamRepository.findAll()
                .stream()
                .map(AdminExamResponse::fromEntity).toList();
    }

    @Override
    public AdminExamResponse getExamById(int examId)
    {
        return adminExamRepository.findById(examId)
                .map(AdminExamResponse::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Exam with id " + examId + " not found"));
    }

    @Override
    public AdminExamResponse createExam(AdminExamRequest request)
    {
        //endTime > startTime
        if(request.getStartTime() != null && request.getEndTime() != null)
        {
            if(request.getStartTime().isAfter(request.getEndTime()))
            {
                throw new BusinessException("Start time must be before End time");
            }
        }

        //map request-entity
        Exam exam = new Exam();
        exam.setTitle(request.getTitle());
        exam.setDescription(request.getDescription());
        exam.setType(request.getType());
        exam.setStartTime(request.getStartTime());
        exam.setEndTime(request.getEndTime());
        exam.setDuration(request.getDuration());

        //save
        Exam savedExam = adminExamRepository.save(exam);

        //map entity-response
        return AdminExamResponse.fromEntity(savedExam);
    }

    @Override
    public AdminExamResponse editExam(int examId, AdminExamRequest request)
    {
        Exam exam = adminExamRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam with id " + examId + " not found"));

        if(request.getStartTime() != null && request.getEndTime() != null)
        {
            if(request.getStartTime().isAfter(request.getEndTime()))
            {
                throw new BadRequestException("Start time must be before End time");
            }
        }

        exam.setTitle(request.getTitle());
        exam.setDescription(request.getDescription());
        exam.setType(request.getType());
        exam.setStartTime(request.getStartTime());
        exam.setEndTime(request.getEndTime());
        exam.setDuration(request.getDuration());

        Exam editedExam = adminExamRepository.save(exam);
        return AdminExamResponse.fromEntity(editedExam);
    }

    @Override
    public void deleteExam(int examId)
    {
        Exam exam = adminExamRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam with id " + examId + " not found"));
        adminExamRepository.delete(exam);
    }

    @Override
    public void createQuestion(int examId, AdminQuestionRequest request) {

        Exam exam = adminExamRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));

        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new BadRequestException("Question content must not be blank");
        }

        if (request.getOptions() == null || request.getOptions().isEmpty()) {
            throw new BadRequestException("Options must not be empty");
        }

        boolean hasCorrect = request.getOptions()
                .stream()
                .anyMatch(opt -> Boolean.TRUE.equals(opt.getIsCorrect()));

        if (!hasCorrect) {
            throw new BadRequestException("At least one correct answer is required");
        }

        Question question = new Question();
        question.setExam(exam);
        question.setContent(request.getContent());
        question.setExplanation(request.getExplanation());

        List<Option> options = request.getOptions()
                .stream()
                .map(optReq -> {
                    if (optReq.getContent() == null || optReq.getContent().isBlank()) {
                        throw new BadRequestException("Option content must not be blank");
                    }

                    Option option = new Option();
                    option.setContent(optReq.getContent());
                    option.setIsCorrect(optReq.getIsCorrect());
                    option.setQuestion(question);
                    return option;
                })
                .toList();

        question.setOptions(options);

        questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(int examId, int questionId) {

        Exam exam = adminExamRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

        if (!question.getExam().getId().equals(exam.getId())) {
            throw new BadRequestException("Question does not belong to this exam");
        }

        questionRepository.delete(question);
    }

    @Override
    public void importQuestions(int examId, AdminQuestionImportRequest request) {

        Exam exam = adminExamRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));

        if (request.getQuestions() == null || request.getQuestions().isEmpty()) {
            throw new BadRequestException("Questions list must not be empty");
        }

        List<Question> questions = request.getQuestions().stream().map(qReq -> {

            if (qReq.getOptions() == null || qReq.getOptions().isEmpty()) {
                throw new BadRequestException("Options must not be empty");
            }

            boolean hasCorrect = qReq.getOptions()
                    .stream()
                    .anyMatch(opt -> opt.getIsCorrect());

            if (!hasCorrect) {
                throw new BadRequestException("At least one correct answer is required");
            }

            Question question = new Question();
            question.setExam(exam);
            question.setContent(qReq.getContent());
            question.setExplanation(qReq.getExplanation());

            List<Option> options = qReq.getOptions().stream().map(optReq -> {
                Option option = new Option();
                option.setContent(optReq.getContent());
                option.setIsCorrect(optReq.getIsCorrect());
                option.setQuestion(question);
                return option;
            }).toList();

            question.setOptions(options);

            return question;

        }).toList();

        questionRepository.saveAll(questions);
    }
}
