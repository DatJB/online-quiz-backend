package com.group.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminQuestionResultDTO
{
    private int answerId;
    private String questionContent;
    private String questionExplanation;

    private OptionDTO correctOption;
    private OptionDTO selectedOption;

    private Boolean isCorrect;
}
