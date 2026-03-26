package com.group.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class AdminQuestionRequest {
    @NotBlank
    private String content;

    private String explanation;

    @Valid
    @NotEmpty(message = "Options must not be empty")
    private List<OptionRequest> options;

    @Getter
    @Setter
    public static class OptionRequest {
        @NotBlank
        private String content;
        @NotNull
        private Boolean isCorrect;
    }
}