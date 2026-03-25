package com.group.backend.controller;

import com.group.backend.constant.common.ApiMessageConstant;
import com.group.backend.dto.LoginRequest;
import com.group.backend.dto.LoginResponse;
import com.group.backend.dto.RegisterRequest;
import com.group.backend.dto.ResponseGeneral;
import com.group.backend.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseGeneral<Void> register(@Valid @RequestBody RegisterRequest request) {
    authService.register(request);
    return ResponseGeneral.ofSuccess(ApiMessageConstant.SUCCESS_REGISTER, null);
  }

  @PostMapping("/login")
  public ResponseGeneral<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    LoginResponse loginResponse = authService.login(request);
    return ResponseGeneral.ofSuccess(ApiMessageConstant.SUCCESS_LOGIN, loginResponse);
  }

  @PostMapping("/logout")
  public ResponseGeneral<Void> logout() {
    SecurityContextHolder.clearContext();
    return ResponseGeneral.ofSuccess(ApiMessageConstant.SUCCESS_LOGOUT, null);
  }
}