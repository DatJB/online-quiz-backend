package com.group.backend.service.admin.impl;

import com.group.backend.dto.AdminStatisticsResponse;
import com.group.backend.dto.ExamStudentCountDTO;
import com.group.backend.entity.Attempt;
import com.group.backend.entity.enums.AttemptStatus;
import com.group.backend.repository.admin.AdminExamRepository;
import com.group.backend.repository.attempt.AttemptRepository;
import com.group.backend.service.admin.AdminStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
@RequiredArgsConstructor
@Service
public class AdminStatisticsImpl implements AdminStatisticsService {
    private final AdminExamRepository examRepository;
    private final AttemptRepository attemptRepository;

    public AdminStatisticsResponse getStatistics() {
        long totalExams = examRepository.count();
        long totalAttempts = attemptRepository.count();
        long completedAttempts = attemptRepository.countByStatus(AttemptStatus.COMPLETED);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        int lastMonth = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        long attemptsLastMonth = attemptRepository.countAttemptsByMonth(lastMonth, year);

        Double avgLastMonth = attemptRepository.avgScoreByMonth(lastMonth, year);
        if (avgLastMonth == null) avgLastMonth = 0.0;

        long totalStudents = attemptRepository.countDistinctStudents();

        // distribution
        Map<String, Long> distribution = new HashMap<>();
        for (Object[] row : attemptRepository.scoreDistribution()) {
            distribution.put((String) row[0], (Long) row[1]);
        }

        // trend
        Map<Integer, Double> trend = new HashMap<>();
        for (Object[] row : attemptRepository.scoreTrend()) {
            trend.put((Integer) row[0], (Double) row[1]);
        }

        // students by exam
        List<ExamStudentCountDTO> studentsByExam = new ArrayList<>();
        for (Object[] row : attemptRepository.countStudentsByExam()) {
            studentsByExam.add(new ExamStudentCountDTO(
                    (Integer) row[0],
                    (String) row[1],
                    (Long) row[2]
            ));
        }

        return new AdminStatisticsResponse(
                totalExams,
                totalAttempts,
                completedAttempts,
                attemptsLastMonth,
                avgLastMonth,
                totalStudents,
                distribution,
                trend,
                studentsByExam
        );
    }
}
