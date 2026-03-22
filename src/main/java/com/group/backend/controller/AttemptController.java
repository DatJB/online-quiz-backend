package com.group.backend.controller;

import com.group.backend.dto.AttemptDetailDTO;
import com.group.backend.service.admin.AttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/attempts")
@RequiredArgsConstructor
public class AttemptController
{
    private final AttemptService attemptService;

    @GetMapping("/{attemptId}")
    public ResponseEntity<AttemptDetailDTO> getAttemptDetailById(
            @PathVariable Integer attemptId
    ) {
        return ResponseEntity.ok(
                attemptService.getAttemptDetailById(attemptId)
        );
    }
}
