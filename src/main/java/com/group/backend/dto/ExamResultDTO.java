package com.group.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ExamResultDTO
{
    private Integer attemptId;
    private Float score;
    private Integer totalQuestions;
    private Integer correctAnswers;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long duration;
    private List<QuestionResultDTO> questions;
}
