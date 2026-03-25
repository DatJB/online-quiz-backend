package com.group.backend.exception.business;

import com.group.backend.exception.base.BusinessException;

public class InvalidPasswordException extends BusinessException {

  private static final String ERROR_CODE = "INVALID_PASSWORD_ERROR";

  public InvalidPasswordException() {
    super(ERROR_CODE);
  }
}
