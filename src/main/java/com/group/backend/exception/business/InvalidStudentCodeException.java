package com.group.backend.exception.business;

import com.group.backend.exception.base.BusinessException;

public class InvalidStudentCodeException extends BusinessException {

  private static final String ERROR_CODE = "INVALID_STUDENT_CODE";

  public InvalidStudentCodeException() {
    super(ERROR_CODE);
  }
}
