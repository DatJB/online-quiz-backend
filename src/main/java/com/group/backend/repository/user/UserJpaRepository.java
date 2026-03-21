package com.group.backend.repository.user;

import com.group.backend.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Integer> {

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  boolean existsByStudent_StudentCode(String studentCode);

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);
}
