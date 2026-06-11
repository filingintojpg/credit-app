package com.practice.application_service.repository;

import com.practice.application_service.model.Employment;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class EmploymentRepository {

    private final SessionFactory sessionFactory;

    public EmploymentRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Employment save(Employment record) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            session.persist(record);
            transaction.commit();
            return record;
        }
    }
}
