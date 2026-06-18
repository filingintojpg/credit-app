package com.practice.application_service.dto.request;

import com.practice.application_service.model.enums.MaritalStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CreateApplicationRequest {

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

    @NotNull
    private LocalDate birthDate;

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
}
