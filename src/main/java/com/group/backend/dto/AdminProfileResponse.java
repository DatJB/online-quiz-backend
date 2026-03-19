package com.group.backend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminProfileResponse {

  private String username;
  private String email;
  private String role;
}