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
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            session.persist(employment);
            transaction.commit();
            return employment;
        }
    }

    public Employment findByOrganizationAndPositionAndEmployedAt(String organization, String position, LocalDate employedAt) {
        try (var session = sessionFactory.openSession()) {
            Query<Employment> query = session.createQuery(
                    "FROM Employment e WHERE e.organization = :organization AND e.position = :position AND e.employedAt = :employedAt",
                    Employment.class);
            query.setParameter("organization", organization);
            query.setParameter("position", position);
            query.setParameter("employedAt", employedAt);
            return query.uniqueResultOptional().orElse(null);
        }
    }
}
