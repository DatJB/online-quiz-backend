package com.group.backend.exception;

import com.group.backend.constant.exception.ExceptionConstant;
import com.group.backend.constant.validation.ValidationMessageConstant;
import com.group.backend.dto.ResponseGeneral;
import com.group.backend.exception.base.BaseException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseGeneral<Object>> handleValidationException(
      MethodArgumentNotValidException ex) {

    String errorMessage = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(FieldError::getDefaultMessage)
        .findFirst()
        .orElse(ValidationMessageConstant.VALIDATION_FAIL);

    ResponseGeneral<Object> response = ResponseGeneral.of(
        HttpStatus.BAD_REQUEST.value(),
        errorMessage,
        null,
        LocalDateTime.now().toString()
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
  public ResponseEntity<ResponseGeneral<Object>> handleHttpMessageNotReadableException(
      org.springframework.http.converter.HttpMessageNotReadableException ex) {

    ResponseGeneral<Object> response = ResponseGeneral.of(
        HttpStatus.BAD_REQUEST.value(),
        ValidationMessageConstant.MISSING_REQUEST_BODY,
        null,
        LocalDateTime.now().toString()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ResponseGeneral<Object>> handleBaseException(BaseException ex) {

    String detailMessage = ex.getErrorCode();

    ResponseGeneral<Object> response = ResponseGeneral.of(
        ex.getHttpStatus().value(),
        detailMessage,
        null
    );

    return ResponseEntity.status(ex.getHttpStatus()).body(response);
  }

  @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
  public ResponseEntity<ResponseGeneral<Object>> handleAuthenticationException(Exception ex) {

    String detailMessage = ExceptionConstant.TITLE_AUTHENTICATION_ERROR;

    ResponseGeneral<Object> response = ResponseGeneral.of(
        HttpStatus.UNAUTHORIZED.value(),
        detailMessage,
        null
    );

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseGeneral<Object>> handleException(Exception ex) {

    String detailMessage = ExceptionConstant.GROUP_CODE_SYSTEM;

    ResponseGeneral<Object> response = ResponseGeneral.of(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        detailMessage,
        null
    );

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}

