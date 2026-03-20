package com.group.backend.service;

import com.group.backend.dto.AdminDashboardDTO;
import org.springframework.stereotype.Service;

@Service
public interface AdminDashboardService
{
    AdminDashboardDTO sendSummary();
}
