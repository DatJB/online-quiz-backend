package com.group.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptionDTO
{
    private Integer id;
    private String content;
}
