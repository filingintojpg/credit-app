package com.practice.application_service.repository;

import com.practice.application_service.model.Employment;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class EmploymentRepository {

    private final SessionFactory sessionFactory;

    public EmploymentRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Employment save(Employment employment) {
        sessionFactory.getCurrentSession().persist(employment);
        return employment;
    }
}
