package com.practice.application_service.repository;

import com.practice.application_service.dto.ApplicationDetailsResponse;
import com.practice.application_service.dto.util.ApplicationFilter;
import com.practice.application_service.dto.util.PagedResponse;
import com.practice.application_service.model.Application;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public PagedResponse<ApplicationDetailsResponse> findWithFilters(ApplicationFilter filter) {
        List<ApplicationDetailsResponse> items = findFilteredItems(filter);
        long total = countWithFilters(filter);
        return new PagedResponse<>(items, filter.getPage(), filter.getSize(), total);
    }

    private List<ApplicationDetailsResponse> findFilteredItems(ApplicationFilter filter) {
        var session = sessionFactory.getCurrentSession();
        StringBuilder hql = new StringBuilder("""
        SELECT new com.practice.application_service.dto.ApplicationDetailsResponse(
            a.id, a.phone, a.moneyAmount, a.term,
            d.status, d.updatedAt,
            p.lastName, p.firstName, p.middleName,
            p.series, p.number, p.address, p.maritalStatus, p.birthDate,
            e.organization, e.position, e.employedAt, e.dismissedAt
        )
        FROM Application a
        JOIN a.passport p
        JOIN a.employment e
        JOIN Decision d ON d.application = a
        WHERE 1=1
        """);

        if (filter.getAmount() != null) { hql.append(" AND a.moneyAmount = :amount"); }
        if (filter.getTerm() != null) { hql.append(" AND a.term = :term"); }
        if (filter.getPhone() != null) { hql.append(" AND a.phone = :phone"); }
        if (filter.getStatuses() != null && !filter.getStatuses().isEmpty()) { hql.append(" AND d.status IN :statuses"); }

        Query<ApplicationDetailsResponse> query = session.createQuery(hql.toString(), ApplicationDetailsResponse.class);

        if (filter.getAmount() != null) { query.setParameter("amount", filter.getAmount()); }
        if (filter.getTerm() != null) { query.setParameter("term", filter.getTerm()); }
        if (filter.getPhone() != null) { query.setParameter("phone", filter.getPhone()); }
        if (filter.getStatuses() != null && !filter.getStatuses().isEmpty()) { query.setParameter("statuses", filter.getStatuses()); }

        query.setFirstResult(filter.getPage() * filter.getSize());
        query.setMaxResults(filter.getSize());

        return query.getResultList();
    }

    private long countWithFilters(ApplicationFilter filter) {
        var session = sessionFactory.getCurrentSession();
        StringBuilder hql = new StringBuilder("""
        SELECT COUNT(a)
        FROM Application a
        JOIN a.passport p
        JOIN a.employment e
        JOIN Decision d ON d.application = a
        WHERE 1=1
        """);

        if (filter.getAmount() != null) { hql.append(" AND a.moneyAmount = :amount"); }
        if (filter.getTerm() != null) { hql.append(" AND a.term = :term"); }
        if (filter.getPhone() != null) { hql.append(" AND a.phone = :phone"); }
        if (filter.getStatuses() != null && !filter.getStatuses().isEmpty()) { hql.append(" AND d.status IN :statuses"); }

        Query<Long> query = session.createQuery(hql.toString(), Long.class);

        if (filter.getAmount() != null) { query.setParameter("amount", filter.getAmount()); }
        if (filter.getTerm() != null) { query.setParameter("term", filter.getTerm()); }
        if (filter.getPhone() != null) { query.setParameter("phone", filter.getPhone()); }
        if (filter.getStatuses() != null && !filter.getStatuses().isEmpty()) { query.setParameter("statuses", filter.getStatuses()); }

        return query.getSingleResult();
    }
}
