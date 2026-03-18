package com.group.backend.entity;

import com.group.backend.constant.entity.QuestionEntityConstant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = QuestionEntityConstant.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question
{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = QuestionEntityConstant.COL_ID)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = QuestionEntityConstant.COL_EXAM_ID, nullable = false)
  private Exam exam;

  @Column(name = QuestionEntityConstant.COL_CONTENT, columnDefinition = "TEXT", nullable = false)
  private String content;

  @Column(name = QuestionEntityConstant.COL_EXPLANATION, columnDefinition = "TEXT")
  private String explanation;

  @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
  private List<Option> options;
}