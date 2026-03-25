package com.group.backend.constant.validation;

public class ValidationMessageConstant {

  private ValidationMessageConstant() {
  }

  public static final String MISSING_REQUEST_BODY = "Request body is missing";
  public static final String VALIDATION_FAIL = "Validation failed";

  public static final String USERNAME_NOT_BLANK = "Username cannot be blank";
  public static final String EMAIL_NOT_BLANK = "Email cannot be blank";
  public static final String EMAIL_INVALID = "Email format is invalid";

  public static final String PASSWORD_NOT_BLANK = "Password cannot be blank";
  public static final String PASSWORD_MIN_LENGTH = "Password must be at least 6 characters long";

  public static final String CLASS_NOT_BLANK = "Class cannot be blank";

  public static final String STUDENT_CODE_NOT_BLANK = "Student code cannot be blank";
  public static final String PHONE_INVALID = "Phone number must be between 10 and 11 digits";
}
