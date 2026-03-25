package com.group.backend.exception.business;

import com.group.backend.exception.base.BusinessException;

public class InvalidUsernameException extends BusinessException {

  private static final String ERROR_CODE = "INVALID_USERNAME";

  public InvalidUsernameException() {
    super(ERROR_CODE);
  }
}
