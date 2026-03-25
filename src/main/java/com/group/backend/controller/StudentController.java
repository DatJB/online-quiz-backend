package com.group.backend.controller;

import com.group.backend.constant.common.ApiMessageConstant;
import com.group.backend.dto.MyAttemptDTO;
import com.group.backend.dto.PasswordUpdateRequest;
import com.group.backend.dto.ResponseGeneral;
import com.group.backend.dto.StudentProfileResponse;
import com.group.backend.entity.Attempt;
import com.group.backend.service.student.StudentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

  private final StudentService studentService;

  @GetMapping("/profile")
  public ResponseEntity<ResponseGeneral<StudentProfileResponse>> getProfile() {
    return ResponseEntity.ok(ResponseGeneral.ofSuccess(
        ApiMessageConstant.SUCCESS_GET_PROFILE,
        studentService.getMyProfile()
    ));
  }

  @GetMapping("/results")
  public ResponseEntity<ResponseGeneral<List<MyAttemptDTO>>> getResults() {
    return ResponseEntity.ok(ResponseGeneral.ofSuccess(
        ApiMessageConstant.SUCCESS_GET_RESULT,
        studentService.getMyResults()
    ));
  }

  @PutMapping("/profile/password")
  public ResponseEntity<ResponseGeneral<Void>> updatePassword(
      @Valid @RequestBody PasswordUpdateRequest request) {
    studentService.changePassword(request);
    return ResponseEntity.ok(ResponseGeneral.ofSuccess(
        ApiMessageConstant.SUCCESS_CHANGE_PASSWORD,
        null
    ));
  }
}