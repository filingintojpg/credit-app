package com.practice.application_service.controller;

import com.practice.application_service.dto.ApplicationDetailsResponse;
import com.practice.application_service.dto.ApplicationRequest;
import com.practice.application_service.dto.ApplicationStatusResponse;
import com.practice.application_service.dto.util.ApplicationFilter;
import com.practice.application_service.dto.util.PagedResponse;
import com.practice.application_service.model.enums.DecisionStatus;
import com.practice.application_service.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApplicationStatusResponse> createApplication(@Valid @RequestBody ApplicationRequest request) {
        return ResponseEntity.ok(applicationService.createApplication(request));
    }

    @GetMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationStatusResponse> getStatus(@PathVariable Long applicationId) {
        return ResponseEntity.ok(applicationService.getStatus(applicationId));
    }

    @GetMapping
    public ResponseEntity<PagedResponse<ApplicationDetailsResponse>> getApplications(
            @Valid @ModelAttribute ApplicationFilter filter) {
        return ResponseEntity.ok(applicationService.getApplications(filter));
    }
}
