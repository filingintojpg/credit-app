package com.practice.application_service.dto.response;

import com.practice.application_service.model.enums.DecisionStatus;
import com.practice.application_service.model.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDetailsResponse {

    private Long applicationId;
    private String phone;
    private BigDecimal amount;
    private Integer term;
    private DecisionStatus status;
    private LocalDateTime updatedAt;

    private String lastName;
    private String firstName;
    private String middleName;
    private String passportSeries;
    private String passportNumber;
    private String address;
    private MaritalStatus maritalStatus;
    private LocalDate birthDate;

    private String organization;
    private String position;
    private LocalDate employedAt;
    private LocalDate dismissedAt;
}
