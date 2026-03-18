package com.group.backend.entity;

import com.group.backend.constant.entity.AttemptEntityConstant;
import com.group.backend.entity.enums.AttemptStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = AttemptEntityConstant.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attempt
{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = AttemptEntityConstant.COL_ID)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = AttemptEntityConstant.COL_USER_ID, nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = AttemptEntityConstant.COL_EXAM_ID, nullable = false)
  private Exam exam;

  @Column(name = AttemptEntityConstant.COL_START_TIME)
  private LocalDateTime startTime;

  @Column(name = AttemptEntityConstant.COL_END_TIME)
  private LocalDateTime endTime;

  @Column(name = AttemptEntityConstant.COL_SCORE)
  private Float score;

  @Column(name = AttemptEntityConstant.COL_TOTAL_QUESTIONS)
  private Integer totalQuestions;

  @Column(name = AttemptEntityConstant.COL_CORRECT_ANSWERS)
  private Integer correctAnswers;

  @Enumerated(EnumType.STRING)
  @Column(name = AttemptEntityConstant.COL_STATUS, nullable = false)
  private AttemptStatus status;
}