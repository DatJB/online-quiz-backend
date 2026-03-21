package com.group.backend.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
@Getter
@Setter
public class AdminQuestionImportRequest {
    @NotEmpty(message = "Questions must not be empty")
    @Valid
    private List<AdminQuestionRequest> questions;
}
