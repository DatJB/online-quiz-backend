package com.group.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StudentDTO
{
    private int userId;
    private String studentCode;
    private String username;
    private String email;
    private String className;
    private String password;
    private LocalDateTime createdAt;
}
