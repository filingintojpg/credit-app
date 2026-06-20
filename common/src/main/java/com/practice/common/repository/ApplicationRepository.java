package com.practice.common.repository;

import com.practice.common.model.Application;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ApplicationRepository {

    private final SessionFactory sessionFactory;

    public ApplicationRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Application save(Application application) {
        sessionFactory.getCurrentSession().persist(application);
        return application;
    }

    public Application findById(Long id) {
        return sessionFactory.getCurrentSession().get(Application.class, id);
    }
}
