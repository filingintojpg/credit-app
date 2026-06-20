package com.practice.application_service.dto.util;

import com.practice.common.model.enums.DecisionStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationFilter {

    private List<DecisionStatus> statuses;

    @Positive
    private BigDecimal amount;

    @Positive
    private Integer term;

    @Pattern(regexp = "\\d{10,15}")
    private String phone;
}
