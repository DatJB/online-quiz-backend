package com.group.backend.dto;

import com.group.backend.entity.Question;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AdminQuestionResponse {

    private Integer id;
    private String content;
    private String explanation;
    private List<AdminOptionResponse> options;

    public static AdminQuestionResponse fromEntity(Question question) {
        return AdminQuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .explanation(question.getExplanation())
                .options(
                        question.getOptions()
                                .stream()
                                .map(AdminOptionResponse::fromEntity)
                                .toList()
                )
                .build();
    }
}