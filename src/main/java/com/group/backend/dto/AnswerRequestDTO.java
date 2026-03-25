package com.group.backend.dto;

import lombok.Data;

@Data
public class AnswerRequestDTO
{
    private Integer questionId;
    private Integer selectedOptionId;
}
