package com.group.backend.entity;

import com.group.backend.constant.entity.QuestionOptionEntityConstant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = QuestionOptionEntityConstant.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Option
{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = QuestionOptionEntityConstant.COL_ID)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = QuestionOptionEntityConstant.COL_QUESTION_ID, nullable = false)
  private Question question;

  @Column(name = QuestionOptionEntityConstant.COL_CONTENT, columnDefinition = "TEXT", nullable = false)
  private String content;

  @Column(name = QuestionOptionEntityConstant.COL_IS_CORRECT, nullable = false)
  private Boolean isCorrect;
}