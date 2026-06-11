package com.practice.application_service.model;

import com.practice.application_service.model.enums.MaritalStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "client_passports")
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "series", nullable = false, length = 4)
    private String series;

    @Column(name = "number", nullable = false, length = 6)
    private String number;

    @Column(name = "address", nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status", nullable = false)
    private MaritalStatus maritalStatus;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getSeries() { return series; }
    public void setSeries(String series) { this.series = series; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public MaritalStatus getMaritalStatus() { return maritalStatus; }
    public void setMaritalStatus(MaritalStatus maritalStatus) { this.maritalStatus = maritalStatus; }
}
