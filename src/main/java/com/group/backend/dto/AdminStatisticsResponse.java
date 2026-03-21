package com.group.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdminStatisticsResponse {
    private long totalExams;
    private long totalAttempts;
    private long completedAttempts;
    private double averageScore;
}