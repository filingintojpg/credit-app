package com.practice.common.service;

import com.practice.common.model.Application;
import com.practice.common.model.Decision;
import com.practice.common.model.Employment;
import com.practice.common.model.Passport;
import org.hibernate.Hibernate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class CacheService {

    private static final String APPLICATION_KEY_PREFIX = "application:";
    private static final String DECISION_KEY_PREFIX = "decision:";

    private static final Duration TTL = Duration.ofMinutes(10);

    private final RedisTemplate<String, Application> redisApplicationTemplate;
    private final RedisTemplate<String, Decision> redisDecisionTemplate;

    public CacheService(RedisTemplate<String, Application> redisApplicationTemplate,
                        RedisTemplate<String, Decision> redisDecisionTemplate) {
        this.redisApplicationTemplate = redisApplicationTemplate;
        this.redisDecisionTemplate = redisDecisionTemplate;
    }

    public void putApplication(Application application) {
        Passport passport = application.getPassport();
        Employment employment = application.getEmployment();
        Hibernate.initialize(passport);
        Hibernate.initialize(employment);

        redisApplicationTemplate.opsForValue().set(applicationKey(application.getId()), application, TTL);
    }

    public void putDecision(Decision decision) {
        Application application = decision.getApplication();
        Hibernate.initialize(application);
        Hibernate.initialize(application.getPassport());
        Hibernate.initialize(application.getEmployment());

        redisDecisionTemplate.opsForValue().set(decisionKey(decision.getApplication().getId()), decision, TTL);
    }

    public Optional<Application> getApplication(Long applicationId) {
        Application cached = redisApplicationTemplate.opsForValue().get(applicationKey(applicationId));
        return Optional.ofNullable(cached);
    }

    public Optional<Decision> getDecision(Long applicationId) {
        Decision cached = redisDecisionTemplate.opsForValue().get(decisionKey(applicationId));
        return Optional.ofNullable(cached);
    }

    private String applicationKey(Long applicationId) {
        return APPLICATION_KEY_PREFIX + applicationId;
    }

    private String decisionKey(Long applicationId) {
        return DECISION_KEY_PREFIX + applicationId;
    }
}
