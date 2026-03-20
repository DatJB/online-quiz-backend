package com.group.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AdminDashboardDTO
{
    private Long totalExams;
    private Long examDiff;
    private Long totalStudents;
    private Long studentDiff;
    private Long totalAttempts;
    private Long attemptDiff;
    private Double avgPoint;
    private Double avgPointDiff;
    private List<RecentActivityDTO> recentActivities;
}
