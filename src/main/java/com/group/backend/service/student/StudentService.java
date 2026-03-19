package com.group.backend.service.student;

import com.group.backend.dto.PasswordUpdateRequest;
import com.group.backend.dto.StudentProfileResponse;
import com.group.backend.entity.Attempt;
import java.util.List;

public interface StudentService {

  StudentProfileResponse getMyProfile();

  List<Attempt> getMyResults();

  void changePassword(PasswordUpdateRequest request);
}