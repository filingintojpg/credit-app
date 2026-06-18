package com.practice.application_service.dto.util;

import com.practice.application_service.model.enums.DecisionStatus;
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

    @PositiveOrZero
    private int page = 0;

    @Min(1)
    @Max(100)
    private int size = 20;
}
