package com.practice.application_service.controller;

import com.practice.application_service.dto.ApplicationRequest;
import com.practice.application_service.dto.ApplicationStatusResponse;
import com.practice.application_service.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApplicationStatusResponse> createApplication(@Valid @RequestBody ApplicationRequest request) {
        ApplicationStatusResponse response = applicationService.createApplication(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationStatusResponse> getStatus(@PathVariable Long applicationId) {
        ApplicationStatusResponse response = applicationService.getStatus(applicationId);
        return ResponseEntity.ok(response);
    }
}
