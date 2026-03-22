package com.group.backend.service.admin;

import com.group.backend.dto.StudentReportDTO;

public interface ExcelService
{
    byte[] exportResultsToExcel(StudentReportDTO studentReportDTO);
}
