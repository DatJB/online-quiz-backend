package com.group.backend.service.auth;

import com.group.backend.dto.LoginRequest;
import com.group.backend.dto.LoginResponse;
import com.group.backend.dto.RegisterRequest;

public interface AuthService {

  void register(RegisterRequest registerRequest);

  LoginResponse login(LoginRequest loginRequest);
}
