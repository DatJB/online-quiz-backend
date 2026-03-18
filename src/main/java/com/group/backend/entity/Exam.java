package com.group.backend.entity;

import com.group.backend.constant.entity.ExamEntityConstant;
import com.group.backend.entity.enums.ExamType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = ExamEntityConstant.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exam extends BaseJpaAuditing
{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = ExamEntityConstant.COL_ID)
  private Integer id;

  @Column(name = ExamEntityConstant.COL_TITLE, nullable = false, length = ExamEntityConstant.TITLE_MAX_LENGTH)
  private String title;

  @Column(name = ExamEntityConstant.COL_DESCRIPTION, columnDefinition = "TEXT")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = ExamEntityConstant.COL_TYPE, nullable = false)
  private ExamType type;

  @Column(name = ExamEntityConstant.COL_START_TIME)
  private LocalDateTime startTime;

  @Column(name = ExamEntityConstant.COL_END_TIME)
  private LocalDateTime endTime;

  @Column(name = ExamEntityConstant.COL_DURATION, nullable = false)
  private Integer duration;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = ExamEntityConstant.COL_CREATED_BY)
  private User createdBy;
}