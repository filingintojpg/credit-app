package com.practice.application_service.dto;

import com.practice.application_service.model.enums.DecisionStatus;
import com.practice.application_service.model.enums.MaritalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ApplicationDetailsResponse {

    private Long applicationId;

    private String phone;
    private BigDecimal amount;
    private Integer term;
    private DecisionStatus status;

    private String lastName;
    private String firstName;
    private String middleName;
    private String passportSeries;
    private String passportNumber;
    private String address;
    private MaritalStatus maritalStatus;

    private String organization;
    private String position;
    private LocalDate employedAt;
    private LocalDate dismissedAt;

    public ApplicationDetailsResponse(Long applicationId, String phone, BigDecimal amount, Integer term, DecisionStatus status,
                              String lastName, String firstName, String middleName,
                              String passportSeries, String passportNumber, String address, MaritalStatus maritalStatus,
                              String organization, String position, LocalDate employedAt, LocalDate dismissedAt) {
        this.applicationId = applicationId;
        this.phone = phone;
        this.amount = amount;
        this.term = term;
        this.status = status;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
        this.address = address;
        this.maritalStatus = maritalStatus;
        this.organization = organization;
        this.position = position;
        this.employedAt = employedAt;
        this.dismissedAt = dismissedAt;
    }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Integer getTerm() { return term; }
    public void setTerm(Integer term) { this.term = term; }

    public DecisionStatus getStatus() { return status; }
    public void setStatus(DecisionStatus status) { this.status = status; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getPassportSeries() { return passportSeries; }
    public void setPassportSeries(String passportSeries) { this.passportSeries = passportSeries; }

    public String getPassportNumber() { return passportNumber; }
    public void setPassportNumber(String passportNumber) { this.passportNumber = passportNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public MaritalStatus getMaritalStatus() { return maritalStatus; }
    public void setMaritalStatus(MaritalStatus maritalStatus) { this.maritalStatus = maritalStatus; }

    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public LocalDate getEmployedAt() { return employedAt; }
    public void setEmployedAt(LocalDate employedAt) { this.employedAt = employedAt; }

    public LocalDate getDismissedAt() { return dismissedAt; }
    public void setDismissedAt(LocalDate dismissedAt) { this.dismissedAt = dismissedAt; }
}
