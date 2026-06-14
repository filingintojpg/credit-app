package com.practice.decision_service.repository;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public class ApplicationRepository {

    private final SessionFactory sessionFactory;

    public ApplicationRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public ApplicationData findApplicationData(Long applicationId) {
        try (var session = sessionFactory.openSession()) {
            var result = session.createNativeQuery("""
                SELECT a.money_amount, a.term, p.birth_date
                FROM client_applications a
                JOIN client_passports p ON a.passport_id = p.id
                WHERE a.id = :applicationId
                """, Object[].class)
                    .setParameter("applicationId", applicationId)
                    .uniqueResult();

            BigDecimal amount = (BigDecimal) result[0];
            Integer term = (Integer) result[1];
            LocalDate birthDate = ((java.sql.Date) result[2]).toLocalDate();

            return new ApplicationData(amount, term, birthDate);
        }
    }

    public record ApplicationData(BigDecimal amount, Integer term, LocalDate birthDate) {}
}
