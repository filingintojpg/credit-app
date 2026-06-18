package com.practice.application_service.dto.request;

import com.practice.application_service.dto.util.ApplicationFilter;
import com.practice.application_service.dto.util.Pagination;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetApplicationRequest {

    @Valid
    private ApplicationFilter filter = new ApplicationFilter();

    @Valid
    private Pagination pagination = new Pagination();
}
