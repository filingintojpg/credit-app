package com.practice.application_service.dto;

import com.practice.application_service.model.enums.MaritalStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ApplicationRequest {

    @NotBlank
    private String lastName;

    @NotBlank
    private String firstName;

    private String middleName;

    @NotBlank
    @Pattern(regexp = "\\d{4}")
    private String passportSeries;

    @NotBlank
    @Pattern(regexp = "\\d{6}")
    private String passportNumber;

    @NotNull
    private MaritalStatus maritalStatus;

    @NotBlank
    private String address;

    @NotBlank
    @Pattern(regexp = "\\d{10,15}")
    private String phone;

    @NotBlank
    private String organization;

    @NotBlank
    private String position;

    @NotNull
    private LocalDate employedAt;

    private LocalDate dismissedAt;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    @Positive
    private Integer term;

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

    public MaritalStatus getMaritalStatus() { return maritalStatus; }
    public void setMaritalStatus(MaritalStatus maritalStatus) { this.maritalStatus = maritalStatus; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public LocalDate getEmployedAt() { return employedAt; }
    public void setEmployedAt(LocalDate employedAt) { this.employedAt = employedAt; }

    public LocalDate getDismissedAt() { return dismissedAt; }
    public void setDismissedAt(LocalDate dismissedAt) { this.dismissedAt = dismissedAt; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Integer getTerm() { return term; }
    public void setTerm(Integer term) { this.term = term; }
}
