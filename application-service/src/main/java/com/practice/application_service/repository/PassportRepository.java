package com.practice.application_service.repository;

import com.practice.application_service.model.Passport;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class PassportRepository {

    private final SessionFactory sessionFactory;

    public PassportRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Passport save(Passport passport) {
        sessionFactory.getCurrentSession().persist(passport);
        return passport;
    }

    public Passport findBySeriesAndNumber(String series, String number) {
        Query<Passport> query = sessionFactory.getCurrentSession().createQuery(
                "FROM Passport p WHERE p.series = :series AND p.number = :number", Passport.class);
        query.setParameter("series", series);
        query.setParameter("number", number);
        return query.uniqueResultOptional().orElse(null);
    }
}
