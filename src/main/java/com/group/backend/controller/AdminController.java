package com.group.backend.controller;

import com.group.backend.constant.common.ApiMessageConstant;
import com.group.backend.dto.AdminProfileResponse;
import com.group.backend.dto.PasswordUpdateRequest;
import com.group.backend.dto.ResponseGeneral;
import com.group.backend.service.admin.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

  private final AdminService adminService;

  @GetMapping("/profile")
  public ResponseEntity<ResponseGeneral<AdminProfileResponse>> getProfile() {
    return ResponseEntity.ok(ResponseGeneral.ofSuccess(
        ApiMessageConstant.SUCCESS_GET_PROFILE,
        adminService.getMyProfile()
    ));
  }

  @PutMapping("/profile/password")
  public ResponseEntity<ResponseGeneral<Void>> updatePassword(
      @Valid @RequestBody PasswordUpdateRequest request) {

    adminService.changePassword(request);

    return ResponseEntity.ok(ResponseGeneral.ofSuccess(
        ApiMessageConstant.SUCCESS_CHANGE_PASSWORD,
        null
    ));
  }
}