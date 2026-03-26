package com.group.backend.dto;

import com.group.backend.entity.Exam;

import java.time.LocalDateTime;
import com.group.backend.entity.enums.ExamType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminExamResponse {
    private Integer id;
    private String title;
    private String description;
    private ExamType type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer duration;

    public static AdminExamResponse fromEntity(Exam exam) {
        return AdminExamResponse.builder()
                .id(exam.getId())
                .title(exam.getTitle())
                .description(exam.getDescription())
                .type(exam.getType())
                .startTime(exam.getStartTime())
                .endTime(exam.getEndTime())
                .duration(exam.getDuration())
                .build();
    }
}
