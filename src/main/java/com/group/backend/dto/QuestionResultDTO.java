package com.group.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionResultDTO
{
    private int answerId;
    private String questionContent;
    private String questionExplanation;

    private OptionDTO correctOption;
    private OptionDTO selectedOption;

    private Boolean isCorrect;
}
