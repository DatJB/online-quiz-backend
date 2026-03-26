package com.group.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class AdminStatisticsResponse {
    private long totalExams;
    private long totalAttempts;
    private long completedAttempts;

    private long attemptsLastMonth;
    private double avgScoreLastMonth;

    private long totalStudents;

    private Map<String, Long> scoreDistribution;
    private Map<Integer, Double> scoreTrend;

    private List<ExamStudentCountDTO> studentsByExam;
}