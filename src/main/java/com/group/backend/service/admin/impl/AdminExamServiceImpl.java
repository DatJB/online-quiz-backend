package com.group.backend.service.admin.impl;

import java.util.*;

import com.group.backend.dto.*;
import com.group.backend.entity.Exam;
import com.group.backend.exception.base.BusinessException;
import com.group.backend.exception.business.BadRequestException;
import com.group.backend.repository.admin.OptionRepository;
import com.group.backend.repository.admin.QuestionRepository;
import com.group.backend.service.admin.AdminExamService;
import com.group.backend.repository.admin.AdminExamRepository;
import com.group.backend.exception.business.ResourceNotFoundException;
import com.group.backend.entity.Option;
import com.group.backend.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


@Service
@RequiredArgsConstructor
public class AdminExamServiceImpl implements AdminExamService {
    private final AdminExamRepository adminExamRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

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
    public void editQuestion(int examId, int questionId, AdminQuestionRequest request) {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

        if (!question.getExam().getId().equals(examId)) {
            throw new BadRequestException("Question does not belong to this exam");
        }

        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new BadRequestException("Question content must not be blank");
        }

        question.setContent(request.getContent());
        question.setExplanation(request.getExplanation());

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
    public void importQuestions(int examId, MultipartFile file) {

        Exam exam = adminExamRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(0);
            List<Question> questions = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Question question = new Question();
                question.setExam(exam);
                question.setContent(row.getCell(0).toString());
                question.setExplanation(row.getCell(1).toString());

                List<Option> options = new ArrayList<>();

                String correct = row.getCell(6).toString();

                if (!List.of("A","B","C","D").contains(correct.toUpperCase())) {
                    throw new BadRequestException("Invalid correct answer at row " + i);
                }
                options.add(createOption(question, row.getCell(2).toString(), "A".equalsIgnoreCase(correct)));
                options.add(createOption(question, row.getCell(3).toString(), "B".equalsIgnoreCase(correct)));
                options.add(createOption(question, row.getCell(4).toString(), "C".equalsIgnoreCase(correct)));
                options.add(createOption(question, row.getCell(5).toString(), "D".equalsIgnoreCase(correct)));

                question.setOptions(options);
                questions.add(question);
            }

            questionRepository.saveAll(questions);

        } catch (Exception e) {
            throw new BadRequestException("Invalid Excel file");
        }
    }
    private Option createOption(Question question, String content, boolean isCorrect) {
        Option option = new Option();
        option.setContent(content);
        option.setIsCorrect(isCorrect);
        option.setQuestion(question);
        return option;
    }
    @Override
    public List<AdminQuestionResponse> getExamQuestions(int examId) {

        List<Question> questions = questionRepository.findByExamId(examId);

        if (questions.isEmpty() && !adminExamRepository.existsById(examId)) {
            throw new ResourceNotFoundException("Exam not found");
        }

        return questions.stream()
                .map(AdminQuestionResponse::fromEntity)
                .toList();
    }

    @Override
    public void editOption(int examId, int questionId, int optionId, AdminOptionRequest request) {

        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found"));

        if (!option.getQuestion().getId().equals(questionId) ||
                !option.getQuestion().getExam().getId().equals(examId)) {
            throw new BadRequestException("Option does not belong to this question/exam");
        }

        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new BadRequestException("Option content must not be blank");
        }

        option.setContent(request.getContent());

        if (Boolean.TRUE.equals(request.getIsCorrect())) {

            List<Option> options = optionRepository.findByQuestionId(questionId);
            for (Option opt : options) {
                opt.setIsCorrect(false);
            }

            optionRepository.saveAll(options);

            option.setIsCorrect(true);

        } else {
            option.setIsCorrect(false);
        }

        optionRepository.save(option);
    }
}
