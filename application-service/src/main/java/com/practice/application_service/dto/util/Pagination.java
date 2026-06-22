package com.practice.application_service.dto.util;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Pagination {

    @PositiveOrZero
    private int page = 0;

    @Min(1)
    @Max(100)
    private int size = 20;
}
