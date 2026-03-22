package com.group.backend.service.admin;

import com.group.backend.dto.AttemptDTO;
import com.group.backend.dto.AttemptDetailDTO;

import java.util.List;

public interface AttemptService
{
    List<AttemptDTO> getResultsByStudentId(Integer studentId);

    AttemptDetailDTO getAttemptDetailById(Integer attemptId);

    byte[] exportResults(Integer studentId);
}
