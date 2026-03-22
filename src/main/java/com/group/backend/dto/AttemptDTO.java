package com.group.backend.dto;

import com.group.backend.entity.Answer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AttemptDTO
{
    private Integer id;
    private String examTitle;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Long timeSpent;

    private Float score;
    private Integer totalQuestions;
    private Integer correctAnswer;
    private Float correctRate;

    private String status;
}
