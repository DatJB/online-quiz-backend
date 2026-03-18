package com.group.backend.entity;

import com.group.backend.constant.entity.StudentEntityConstant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = StudentEntityConstant.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentEntity {

  @Id
  @Column(name = StudentEntityConstant.COL_USER_ID)
  private Integer userId;

  @OneToOne
  @MapsId
  @JoinColumn(name = StudentEntityConstant.COL_USER_ID)
  private UserEntity user;

  @Column(name = StudentEntityConstant.COL_STUDENT_CODE, unique = true, nullable = false, length = StudentEntityConstant.STUDENT_CODE_MAX_LENGTH)
  private String studentCode;

  @Column(name = StudentEntityConstant.COL_CLASS_NAME)
  private String className;

  @Column(name = StudentEntityConstant.COL_PHONE, length = StudentEntityConstant.PHONE_MAX_LENGTH)
  private String phone;
}