package com.group.backend.dto;

import com.group.backend.entity.Option;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminOptionResponse {

    private Integer id;
    private String content;
    private Boolean isCorrect;

    public static AdminOptionResponse fromEntity(Option option) {
        return AdminOptionResponse.builder()
                .id(option.getId())
                .content(option.getContent())
                .isCorrect(option.getIsCorrect())
                .build();
    }
}