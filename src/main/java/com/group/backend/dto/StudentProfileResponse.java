package com.group.backend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentProfileResponse {
  private String username;
  private String email;
  private String studentCode;
  private String className;
  private String phone;
}