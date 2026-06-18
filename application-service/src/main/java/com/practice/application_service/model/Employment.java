package com.practice.application_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "client_employments")
@Getter
@Setter
@NoArgsConstructor
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
}
