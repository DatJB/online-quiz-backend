package com.group.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SubmitAttemptResponseDTO
{
    private Integer attemptId;
    private Float score;
    private Integer totalQuestions;
    private Integer correctAnswers;
    private String status;
    private LocalDateTime endTime;
}
