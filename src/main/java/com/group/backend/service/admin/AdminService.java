package com.group.backend.service.admin;

import com.group.backend.dto.AdminProfileResponse;
import com.group.backend.dto.PasswordUpdateRequest;

public interface AdminService {

  AdminProfileResponse getMyProfile();

  void changePassword(PasswordUpdateRequest request);
}