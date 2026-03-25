package com.group.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class SubmitAttemptRequestDTO
{
    private List<AnswerRequestDTO> answers;
}
