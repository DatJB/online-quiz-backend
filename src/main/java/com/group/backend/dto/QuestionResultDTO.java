package com.group.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionResultDTO
{
    private Integer questionId;
    private String content;
    private String explanation;
    private List<OptionResultDTO> options;
    private Integer selectedOptionId;
    private Boolean isCorrect;
}
