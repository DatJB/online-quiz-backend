package com.group.backend.dto;

import com.group.backend.constant.validation.ValidationMessageConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterRequest {

  @NotBlank(message = ValidationMessageConstant.USERNAME_NOT_BLANK)
  @Size(min = 3, max = 50)
  private String username;

  @NotBlank(message = ValidationMessageConstant.EMAIL_NOT_BLANK)
  @Email(message = ValidationMessageConstant.EMAIL_INVALID)
  private String email;

  @NotBlank(message = ValidationMessageConstant.PASSWORD_NOT_BLANK)
  @Size(min = 6, message = ValidationMessageConstant.PASSWORD_MIN_LENGTH)
  private String password;

  @NotBlank(message = ValidationMessageConstant.STUDENT_CODE_NOT_BLANK)
  private String studentCode;

  @NotBlank(message = ValidationMessageConstant.CLASS_NOT_BLANK)
  private String className;


  @Pattern(regexp = "^\\d{10,11}$", message = ValidationMessageConstant.PHONE_INVALID)
  private String phone;
}