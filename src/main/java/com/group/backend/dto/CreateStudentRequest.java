package com.group.backend.dto;

import lombok.Data;

@Data
public class CreateStudentRequest
{
    private String studentCode;
    private String className;
    private String username;
    private String email;
    private String password;
}
