package com.group.backend.service.impl;

import com.group.backend.dto.AdminDashboardDTO;
import com.group.backend.dto.RecentActivityDTO;
import com.group.backend.entity.enums.Role;
import com.group.backend.repository.StudentAttemptRepository;
import com.group.backend.repository.StudentExamRepository;
import com.group.backend.repository.UserRepository;
import com.group.backend.service.AdminDashboardService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService
{
    private final UserRepository userRepository;
    private final StudentAttemptRepository studentAttemptRepository;
    private final StudentExamRepository studentExamRepository;

    @Override
    @Transactional
    public AdminDashboardDTO sendSummary()
    {
        int thisMonth = LocalDate.now().getMonthValue();
        int thisYear = LocalDate.now().getYear();
        int lastMonth = thisMonth == 1 ? 12 : thisMonth - 1;
        int lastYear = thisMonth == 1 ? thisYear - 1 : thisYear;

        Long totalExams = studentExamRepository.countExamByMonthAndYear(thisMonth, thisYear);
        Long totalStudents = userRepository.countStudentByMonthAndYear(thisMonth, thisYear, Role.STUDENT);
        Long totalAttempts = studentAttemptRepository.countAttemptByMonthAndYear(thisMonth, thisYear);
        Double avgPoint = studentAttemptRepository.avgPointByMonthAndYear(thisMonth, thisYear);

        Long lastExams = studentExamRepository.countExamByMonthAndYear(lastMonth, lastYear);
        Long lastStudents = userRepository.countStudentByMonthAndYear(lastMonth, lastYear, Role.STUDENT);
        Long lastAttempts = studentAttemptRepository.countAttemptByMonthAndYear(lastMonth, lastYear);
        Double lastAvgPoint = studentAttemptRepository.avgPointByMonthAndYear(lastMonth, lastYear);

        List<RecentActivityDTO> recentActivities = studentAttemptRepository.findRecentAttempts()
                .stream()
                .map(a -> RecentActivityDTO.builder()
                        .attemptId(a.getId())
                        .userId(a.getUser().getId())
                        .username(a.getUser().getUsername())
                        .examTitle(a.getExam().getTitle())
                        .point(a.getScore())
                        .submitTime(a.getEndTime())
                        .build())
                .toList();

        return AdminDashboardDTO.builder()
                .totalExams(totalExams)
                .examDiff(totalExams - lastExams)
                .totalStudents(totalStudents)
                .studentDiff(totalStudents - lastStudents)
                .totalAttempts(totalAttempts)
                .attemptDiff(totalAttempts - lastAttempts)
                .avgPoint(avgPoint)
                .avgPointDiff(lastAvgPoint == null ? avgPoint : avgPoint - lastAvgPoint)
                .recentActivities(recentActivities)
                .build();
    }
}
