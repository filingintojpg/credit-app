package com.practice.common.repository;

import com.practice.common.model.Decision;
import com.practice.common.model.enums.DecisionStatus;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class DecisionRepository {

    private final SessionFactory sessionFactory;

    public DecisionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Decision decision) {
        sessionFactory.getCurrentSession().persist(decision);
    }

    public Decision findByApplicationId(Long applicationId) {
        Query<Decision> query = sessionFactory.getCurrentSession().createQuery(
                "FROM Decision d WHERE d.application.id = :appId", Decision.class);
        query.setParameter("appId", applicationId);
        return query.uniqueResultOptional().orElse(null);
    }

    public void updateStatus(Long applicationId, DecisionStatus status) {
        sessionFactory.getCurrentSession().createMutationQuery(
                        "UPDATE Decision d SET d.status = :status, d.updatedAt = :updatedAt WHERE d.application.id = :applicationId")
                .setParameter("status", status)
                .setParameter("updatedAt", LocalDateTime.now())
                .setParameter("applicationId", applicationId)
                .executeUpdate();
    }
}
