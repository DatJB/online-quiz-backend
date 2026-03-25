package com.group.backend.exception.business;

import com.group.backend.constant.exception.ExceptionConstant;
import com.group.backend.exception.base.BusinessException;
import org.springframework.http.HttpStatus;

public class ExamNotFoundException extends BusinessException
{
    private static final String ERROR_CODE = "Exam not found";

    public ExamNotFoundException()
    {
        super(ERROR_CODE, HttpStatus.NOT_FOUND, ExceptionConstant.TITLE_NOT_FOUND);
    }
}