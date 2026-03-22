package com.group.backend.service.admin.impl;

import com.group.backend.dto.AttemptDTO;
import com.group.backend.dto.StudentReportDTO;
import com.group.backend.service.admin.ExcelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;

@Service
@AllArgsConstructor
public class ExcelServiceImpl implements ExcelService
{
    @Override
    public byte[] exportResultsToExcel(StudentReportDTO report) {
        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Student Report");
            int rowIdx = 0;

            Font boldFont = workbook.createFont();
            boldFont.setBold(true);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(boldFont);

            Row row0 = sheet.createRow(rowIdx++);
            row0.createCell(0).setCellValue("Student Name");
            row0.createCell(1).setCellValue(report.getStudentName());

            Row row1 = sheet.createRow(rowIdx++);
            row1.createCell(0).setCellValue("Student Code");
            row1.createCell(1).setCellValue(report.getStudentCode());

            Row row2 = sheet.createRow(rowIdx++);
            row2.createCell(0).setCellValue("Email");
            row2.createCell(1).setCellValue(report.getEmail());

            Row row3 = sheet.createRow(rowIdx++);
            row3.createCell(0).setCellValue("Average Score");
            row3.createCell(1).setCellValue(report.getAverageScore());

            rowIdx++;

            Row header = sheet.createRow(rowIdx++);

            String[] headers = {
                    "Exam",
                    "Start Time",
                    "End Time",
                    "Duration (min)",
                    "Correct",
                    "Total",
                    "Rate (%)",
                    "Score",
                    "Status"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            for (AttemptDTO attempt : report.getAttempts()) {
                Row row = sheet.createRow(rowIdx++);

                int col = 0;

                row.createCell(col++).setCellValue(attempt.getExamTitle());

                row.createCell(col++).setCellValue(
                        attempt.getStartTime() != null ? attempt.getStartTime().toString() : ""
                );

                row.createCell(col++).setCellValue(
                        attempt.getEndTime() != null ? attempt.getEndTime().toString() : ""
                );

                row.createCell(col++).setCellValue(
                        attempt.getTimeSpent() != null ? attempt.getTimeSpent() : 0
                );

                row.createCell(col++).setCellValue(
                        attempt.getCorrectAnswer() != null ? attempt.getCorrectAnswer() : 0
                );

                row.createCell(col++).setCellValue(
                        attempt.getTotalQuestions() != null ? attempt.getTotalQuestions() : 0
                );

                row.createCell(col++).setCellValue(
                        attempt.getCorrectRate() != null ? attempt.getCorrectRate() : 0
                );

                row.createCell(col++).setCellValue(
                        attempt.getScore() != null ? attempt.getScore() : 0
                );

                row.createCell(col++).setCellValue(
                        attempt.getStatus() != null ? attempt.getStatus() : ""
                );
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error while generating Excel", e);
        }
    }
}
