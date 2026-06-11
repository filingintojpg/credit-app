package com.practice.application_service.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "client_applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "money_amount", nullable = false)
    private BigDecimal moneyAmount;

    @Column(name = "term", nullable = false)
    private Integer term;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passport_id", nullable = false)
    private Passport passport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_id", nullable = false)
    private EmploymentRecord employment;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public BigDecimal getMoneyAmount() { return moneyAmount; }
    public void setMoneyAmount(BigDecimal moneyAmount) { this.moneyAmount = moneyAmount; }

    public Integer getTerm() { return term; }
    public void setTerm(Integer term) { this.term = term; }

    public Passport getPassport() { return passport; }
    public void setPassport(Passport passport) { this.passport = passport; }

    public EmploymentRecord getEmployment() { return employment; }
    public void setEmployment(EmploymentRecord employment) { this.employment = employment; }
}
