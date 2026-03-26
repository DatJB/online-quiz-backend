package com.group.backend.service.admin.impl;

import com.group.backend.dto.AdminStatisticsResponse;
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

        List<Attempt> attempts = attemptRepository.findAll();

        double avgScore = attempts.stream()
                .filter(a -> a.getScore() != null)
                .mapToDouble(Attempt::getScore)
                .average()
                .orElse(0.0);

        return new AdminStatisticsResponse(
                totalExams,
                totalAttempts,
                completedAttempts,
                avgScore
        );
    }
}
