package com.practice.application_service.dto.util;

import com.practice.application_service.model.enums.DecisionStatus;

import java.math.BigDecimal;
import java.util.List;

public class ApplicationFilter {

    private List<DecisionStatus> statuses;
    private BigDecimal amount;
    private Integer term;
    private String phone;
    private int page;
    private int size;

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
