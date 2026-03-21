package com.group.backend.dto;

import com.group.backend.constant.validation.ValidationMessageConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateRequest {

  @NotBlank(message = ValidationMessageConstant.PASSWORD_NOT_BLANK)
  private String currentPassword;

  @NotBlank(message = ValidationMessageConstant.PASSWORD_NOT_BLANK)
  @Size(min = 6, message = ValidationMessageConstant.PASSWORD_MIN_LENGTH)
  private String newPassword;
}