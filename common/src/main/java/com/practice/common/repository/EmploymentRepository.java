package com.practice.common.repository;

import com.practice.common.model.Employment;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class EmploymentRepository {

    private final SessionFactory sessionFactory;

    public EmploymentRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Employment employment) {
        sessionFactory.getCurrentSession().persist(employment);
    }
}
