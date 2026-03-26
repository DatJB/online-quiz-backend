package com.group.backend.entity;

import com.group.backend.constant.entity.UserEntityConstant;
import com.group.backend.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = UserEntityConstant.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseJpaAuditing
{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = UserEntityConstant.COL_ID)
  private Integer id;

  @Column(name = UserEntityConstant.COL_USERNAME, nullable = false, length = UserEntityConstant.USERNAME_MAX_LENGTH)
  private String username;

  @Column(name = UserEntityConstant.COL_EMAIL, unique = true, nullable = false, length = UserEntityConstant.EMAIL_MAX_LENGTH)
  private String email;

  @Column(name = UserEntityConstant.COL_PASSWORD, nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = UserEntityConstant.COL_ROLE, nullable = false)
  private Role role;

  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
  private Student student;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Attempt> attempts;

  @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
  private List<Exam> createdExams;
}
