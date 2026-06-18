package com.practice.application_service.repository;

import com.practice.application_service.dto.response.ApplicationDetailsResponse;
import com.practice.application_service.dto.util.ApplicationFilter;
import com.practice.application_service.dto.util.PagedResponse;
import com.practice.application_service.model.Application;
import com.practice.application_service.model.Decision;
import com.practice.application_service.model.Employment;
import com.practice.application_service.model.Passport;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ApplicationDetailsResponse> cq = cb.createQuery(ApplicationDetailsResponse.class);

        Root<Decision> decision = cq.from(Decision.class);
        Join<Decision, Application> application = decision.join("application");
        Join<Application, Passport> passport = application.join("passport");
        Join<Application, Employment> employment = application.join("employment");

        cq.select(cb.construct(ApplicationDetailsResponse.class,
                application.get("id"), application.get("phone"), application.get("moneyAmount"), application.get("term"),
                decision.get("status"), decision.get("updatedAt"),
                passport.get("lastName"), passport.get("firstName"), passport.get("middleName"),
                passport.get("series"), passport.get("number"), passport.get("address"),
                passport.get("maritalStatus"), passport.get("birthDate"),
                employment.get("organization"), employment.get("position"),
                employment.get("employedAt"), employment.get("dismissedAt")
        ));

        cq.where(buildPredicates(cb, decision, application, filter));

        return session.createQuery(cq)
                .setFirstResult(filter.getPage() * filter.getSize())
                .setMaxResults(filter.getSize())
                .getResultList();
    }

    private long countWithFilters(ApplicationFilter filter) {
        var session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<Decision> decision = cq.from(Decision.class);
        Join<Decision, Application> application = decision.join("application");

        cq.select(cb.count(decision));
        cq.where(buildPredicates(cb, decision, application, filter));

        return session.createQuery(cq).getSingleResult();
    }

    private Predicate buildPredicates(CriteriaBuilder cb, Root<Decision> decision,
                                      Join<Decision, Application> application, ApplicationFilter filter) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getAmount() != null) {
            predicates.add(cb.equal(application.get("moneyAmount"), filter.getAmount()));
        }
        if (filter.getTerm() != null) {
            predicates.add(cb.equal(application.get("term"), filter.getTerm()));
        }
        if (filter.getPhone() != null) {
            predicates.add(cb.equal(application.get("phone"), filter.getPhone()));
        }
        if (filter.getStatuses() != null && !filter.getStatuses().isEmpty()) {
            predicates.add(decision.get("status").in(filter.getStatuses()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
