package com.group.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentReportDTO
{
    private Integer studentId;
    private String studentName;
    private String email;
    private String studentCode;
    private Double averageScore;
    private List<AttemptDTO> attempts;
}
