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
        ApplicationStatusResponse response = applicationService.createApplication(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationStatusResponse> getStatus(@PathVariable Long applicationId) {
        ApplicationStatusResponse response = applicationService.getStatus(applicationId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PagedResponse<ApplicationDetailsResponse>> getApplications(
            @RequestParam(required = false) List<DecisionStatus> statuses,
            @RequestParam(required = false) BigDecimal amount,
            @RequestParam(required = false) Integer term,
            @RequestParam(required = false) String phone,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        ApplicationFilter filter = new ApplicationFilter();
        filter.setStatuses(statuses);
        filter.setAmount(amount);
        filter.setTerm(term);
        filter.setPhone(phone);
        filter.setPage(page);
        filter.setSize(size);

        return ResponseEntity.ok(applicationService.getApplications(filter));
    }
}
