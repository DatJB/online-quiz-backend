package com.group.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RecentActivityDTO
{
    private Integer attemptId;
    private Integer userId;
    private String username;
    private String examTitle;
    private Float point;
    private LocalDateTime submitTime;
}
