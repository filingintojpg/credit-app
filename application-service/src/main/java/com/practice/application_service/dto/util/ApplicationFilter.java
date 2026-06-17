package com.practice.application_service.dto.util;

import com.practice.application_service.model.enums.DecisionStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.List;

public class ApplicationFilter {

    private List<DecisionStatus> statuses;

    @Positive
    private BigDecimal amount;

    @Positive
    private Integer term;

    private String phone;

    @PositiveOrZero
    private int page = 0;

    @Min(1)
    @Max(100)
    private int size = 20;

    public List<DecisionStatus> getStatuses() { return statuses; }
    public void setStatuses(List<DecisionStatus> statuses) { this.statuses = statuses; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Integer getTerm() { return term; }
    public void setTerm(Integer term) { this.term = term; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
}
