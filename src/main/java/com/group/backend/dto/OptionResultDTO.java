package com.group.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptionResultDTO
{
    private Integer id;
    private String content;
    private Boolean isCorrect;
}
