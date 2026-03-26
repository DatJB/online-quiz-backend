package com.group.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.boot.internal.Abstract;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class ExamStudentCountDTO
{
    private Integer examId;
    private String examTitle;
    private Long studentCount;
}
