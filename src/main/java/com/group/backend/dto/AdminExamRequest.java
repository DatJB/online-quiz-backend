package com.group.backend.dto;

import com.group.backend.entity.enums.ExamType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminExamRequest {
    @NotBlank
    private String title;

    private String description;

    @NotNull
    private ExamType type;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @NotNull
    private Integer duration;

}
