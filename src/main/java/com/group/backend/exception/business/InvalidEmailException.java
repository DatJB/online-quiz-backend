package com.group.backend.exception.business;

import com.group.backend.exception.base.BusinessException;

public class InvalidEmailException extends BusinessException {

  private static final String ERROR_CODE = "INVALID_EMAIL";

  public InvalidEmailException() {
    super(ERROR_CODE);
  }
}
