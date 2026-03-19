package com.group.backend.repository.user.impl;

import com.group.backend.entity.User;
import com.group.backend.repository.user.UserJpaRepository;
import com.group.backend.repository.user.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private final UserJpaRepository userJpaRepository;

  public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
    this.userJpaRepository = userJpaRepository;
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return userJpaRepository.findByUsername(username);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userJpaRepository.findByEmail(email);
  }

  @Override
  public boolean existsByStudent_StudentCode(String studentCode) {
    return userJpaRepository.existsByStudent_StudentCode(studentCode);
  }

  @Override
  public boolean existsByUsername(String username) {
    return userJpaRepository.existsByUsername(username);
  }

  @Override
  public boolean existsByEmail(String email) {
    return userJpaRepository.existsByEmail(email);
  }

  @Override
  public User save(User user) {
    userJpaRepository.save(user);
    return user;
  }
}
