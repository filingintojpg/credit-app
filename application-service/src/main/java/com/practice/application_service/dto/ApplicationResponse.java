package com.practice.application_service.dto;

public class ApplicationResponse {

    private Long applicationId;
    private String status;

    public ApplicationResponse(Long applicationId, String status) {
        this.applicationId = applicationId;
        this.status = status;
    }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
