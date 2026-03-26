package com.group.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminOptionRequest {

    @NotBlank(message = "Option content must not be blank")
    private String content;

    @NotNull(message = "isCorrect must not be null")
    private Boolean isCorrect;
}