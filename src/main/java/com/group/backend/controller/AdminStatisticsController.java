package com.group.backend.controller;

import com.group.backend.dto.AdminStatisticsResponse;
import com.group.backend.dto.ResponseGeneral;
import com.group.backend.service.admin.AdminStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/statistics")
@RequiredArgsConstructor
public class AdminStatisticsController {

    private final AdminStatisticsService adminStatisticsService;

    @GetMapping
    public ResponseEntity<ResponseGeneral<AdminStatisticsResponse>> getStatistics() {
        return ResponseEntity.ok(
                ResponseGeneral.ofSuccess(
                        "Get statistics successfully",
                        adminStatisticsService.getStatistics()
                )
        );
    }
}
