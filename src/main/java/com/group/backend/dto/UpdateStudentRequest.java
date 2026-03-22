package com.group.backend.dto;

import lombok.Data;

@Data
public class UpdateStudentRequest
{
    private String studentCode;
    private String className;
    private String username;
    private String password;
}
