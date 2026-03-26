package com.group.backend.exception.business;

import com.group.backend.exception.base.BusinessException;
import org.springframework.http.HttpStatus;
public class BadRequestException extends BusinessException{
    public static final String ERROR_CODE = "BAD_REQUEST";

    public BadRequestException(String message)
    {
        super(ERROR_CODE, HttpStatus.BAD_REQUEST, message);
    }
}
