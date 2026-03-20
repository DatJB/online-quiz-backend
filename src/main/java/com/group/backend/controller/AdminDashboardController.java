package com.group.backend.controller;

import com.group.backend.dto.AdminDashboardDTO;
import com.group.backend.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboardController
{
    @Autowired
    private AdminDashboardService adminDashboardService;

    @GetMapping("/summary")
    public ResponseEntity<AdminDashboardDTO> sendSummary()
    {
        AdminDashboardDTO summary = adminDashboardService.sendSummary();
        return ResponseEntity.ok(summary);
    }
}
