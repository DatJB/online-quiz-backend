package com.group.backend.service.admin.impl;

import com.group.backend.dto.AdminProfileResponse;
import com.group.backend.dto.PasswordUpdateRequest;
import com.group.backend.entity.User;
import com.group.backend.exception.business.InvalidPasswordException;
import com.group.backend.repository.user.UserRepository;
import com.group.backend.security.CustomUserDetails;
import com.group.backend.service.admin.AdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  private User getCurrentUser() {
    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return userDetails.getUser();
  }

  @Override
  public AdminProfileResponse getMyProfile() {
    User user = getCurrentUser();

    return AdminProfileResponse.builder()
        .username(user.getUsername())
        .email(user.getEmail())
        .role(user.getRole().name())
        .build();
  }

  @Override
  @Transactional
  public void changePassword(PasswordUpdateRequest request) {
    User user = getCurrentUser();

    if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
      throw new InvalidPasswordException();
    }

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user);
  }
}