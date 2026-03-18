package com.group.backend.entity;

import com.group.backend.constant.entity.AnswerEntityConstant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = AnswerEntityConstant.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer
{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = AnswerEntityConstant.COL_ID)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = AnswerEntityConstant.COL_ATTEMPT_ID, nullable = false)
  private Attempt attempt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = AnswerEntityConstant.COL_QUESTION_ID, nullable = false)
  private Question question;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = AnswerEntityConstant.COL_SELECTED_OPTION_ID)
  private Option selectedOption;

  @Column(name = AnswerEntityConstant.COL_IS_CORRECT)
  private Boolean isCorrect;
}