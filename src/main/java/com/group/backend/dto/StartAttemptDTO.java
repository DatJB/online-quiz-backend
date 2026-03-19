package com.group.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StartAttemptDTO
{
    private Integer id;
    private Integer examId;
    private LocalDateTime startTime;
    private String status;
}
