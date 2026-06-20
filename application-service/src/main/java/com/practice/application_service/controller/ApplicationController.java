package com.practice.application_service.controller;

import com.practice.application_service.dto.request.GetApplicationRequestDTO;
import com.practice.application_service.dto.response.ApplicationDetailsResponseDTO;
import com.practice.application_service.dto.request.CreateApplicationRequestDTO;
import com.practice.application_service.dto.response.ApplicationStatusResponseDTO;
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

    @PostMapping("/create")
    public ResponseEntity<ApplicationStatusResponseDTO> createApplication(@Valid @RequestBody CreateApplicationRequestDTO request) {
        return ResponseEntity.ok(applicationService.createApplication(request));
    }

    @GetMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationStatusResponseDTO> getStatus(@PathVariable Long applicationId) {
        return ResponseEntity.ok(applicationService.getStatus(applicationId));
    }

    @PostMapping
    public ResponseEntity<PagedResponse<ApplicationDetailsResponseDTO>> getApplications(@Valid @RequestBody GetApplicationRequestDTO request) {
        return ResponseEntity.ok(applicationService.getApplications(request));
    }
}
