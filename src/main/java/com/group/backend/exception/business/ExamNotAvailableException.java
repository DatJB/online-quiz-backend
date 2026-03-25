package com.group.backend.exception.business;

import com.group.backend.constant.exception.ExceptionConstant;
import com.group.backend.exception.base.BusinessException;
import org.springframework.http.HttpStatus;

public class ExamNotAvailableException extends BusinessException
{
    private static final String ERROR_CODE = "Exam not available at this time";

    public ExamNotAvailableException()
    {
        super(ERROR_CODE, HttpStatus.FORBIDDEN, ExceptionConstant.TITLE_FORBIDDEN);
    }
}
