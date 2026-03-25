package com.group.backend.dto;

import com.group.backend.constant.validation.ValidationMessageConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {

  @NotBlank(message = ValidationMessageConstant.EMAIL_NOT_BLANK)
  @Email(message = ValidationMessageConstant.EMAIL_INVALID)
  private String email;

  @NotBlank(message = ValidationMessageConstant.PASSWORD_NOT_BLANK)
  private String password;
}