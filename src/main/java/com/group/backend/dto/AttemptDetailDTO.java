package com.group.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AttemptDetailDTO
{
    private Integer attemptId;
    private List<QuestionResultDTO> questionResults;
}
