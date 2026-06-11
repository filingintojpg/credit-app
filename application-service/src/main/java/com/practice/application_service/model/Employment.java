package com.practice.application_service.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "client_employments")
public class Employment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organization", nullable = false)
    private String organization;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "employed_at", nullable = false)
    private LocalDate employedAt;

    @Column(name = "dismissed_at")
    private LocalDate dismissedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public LocalDate getEmployedAt() { return employedAt; }
    public void setEmployedAt(LocalDate employedAt) { this.employedAt = employedAt; }

    public LocalDate getDismissedAt() { return dismissedAt; }
    public void setDismissedAt(LocalDate dismissedAt) { this.dismissedAt = dismissedAt; }
}
