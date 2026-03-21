package com.group.backend.repository.user;

import com.group.backend.entity.User;
import java.util.Optional;

public interface UserRepository {

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  boolean existsByStudent_StudentCode(String studentCode);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  User save(User user);
}
