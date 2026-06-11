package com.practice.application_service.repository;

import com.practice.application_service.model.Application;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ApplicationRepository {

    private final SessionFactory sessionFactory;

    public ApplicationRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Application save(Application application) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            session.persist(application);
            transaction.commit();
            return application;
        }
    }

    public Application findById(Long id) {
        try (var session = sessionFactory.openSession()) {
            return session.get(Application.class, id);
        }
    }
}
