package com.practice.common.repository;

import com.practice.common.model.Passport;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PassportRepository {

    private final SessionFactory sessionFactory;

    public PassportRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Passport passport) {
        sessionFactory.getCurrentSession().persist(passport);
    }
}
