package com.practice.decision_service.repository;

import com.practice.decision_service.model.Decision;
import com.practice.decision_service.model.enums.DecisionStatus;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class DecisionRepository {

    private final SessionFactory sessionFactory;

    public DecisionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void updateStatus(Long applicationId, DecisionStatus status) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();

            session.createMutationQuery(
                            "UPDATE Decision d SET d.status = :status, d.updatedAt = :updatedAt WHERE d.applicationId = :applicationId")
                    .setParameter("status", status)
                    .setParameter("updatedAt", LocalDateTime.now())
                    .setParameter("applicationId", applicationId)
                    .executeUpdate();

            transaction.commit();
        }
    }
}
