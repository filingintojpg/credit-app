package com.practice.application_service.controller;

import com.practice.application_service.dto.request.GetApplicationRequest;
import com.practice.application_service.dto.response.ApplicationDetailsResponse;
import com.practice.application_service.dto.request.CreateApplicationRequest;
import com.practice.application_service.dto.response.ApplicationStatusResponse;
import com.practice.application_service.dto.util.PagedResponse;
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
    public ResponseEntity<ApplicationStatusResponse> createApplication(@Valid @RequestBody CreateApplicationRequest request) {
        return ResponseEntity.ok(applicationService.createApplication(request));
    }

    @GetMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationStatusResponse> getStatus(@PathVariable Long applicationId) {
        return ResponseEntity.ok(applicationService.getStatus(applicationId));
    }

    @PostMapping("/filter")
    public ResponseEntity<PagedResponse<ApplicationDetailsResponse>> getApplications(@Valid @RequestBody GetApplicationRequest request) {
        return ResponseEntity.ok(applicationService.getApplications(request));
    }
}
