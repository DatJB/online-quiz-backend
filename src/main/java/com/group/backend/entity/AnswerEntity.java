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
public class AnswerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = AnswerEntityConstant.COL_ID)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = AnswerEntityConstant.COL_ATTEMPT_ID, nullable = false)
  private AttemptEntity attempt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = AnswerEntityConstant.COL_QUESTION_ID, nullable = false)
  private QuestionEntity question;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = AnswerEntityConstant.COL_SELECTED_OPTION_ID)
  private QuestionOptionEntity selectedOption;

  @Column(name = AnswerEntityConstant.COL_IS_CORRECT)
  private Boolean isCorrect;
}