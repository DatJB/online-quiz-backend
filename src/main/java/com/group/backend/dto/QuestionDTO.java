package com.group.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionDTO
{
   private Integer id;
   private String content;
   private String explanation;
   private List<OptionDTO> options;
}
