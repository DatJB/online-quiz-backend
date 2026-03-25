package com.group.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MyAttemptDTO
{
    private Integer attemptId;
    private String examTitle;
    private Float score;
    private Integer totalQuestions;
    private Integer correctAnswers;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}