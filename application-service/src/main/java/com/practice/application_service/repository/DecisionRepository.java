package com.practice.application_service.repository;

import com.practice.application_service.model.Decision;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class DecisionRepository {

    private final SessionFactory sessionFactory;

    public DecisionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Decision save(Decision decision) {
        sessionFactory.getCurrentSession().persist(decision);
        return decision;
    }

    public Decision findByApplicationId(Long applicationId) {
        Query<Decision> query = sessionFactory.getCurrentSession().createQuery(
                "FROM Decision d WHERE d.application.id = :appId", Decision.class);
        query.setParameter("appId", applicationId);
        return query.uniqueResultOptional().orElse(null);
    }
}
