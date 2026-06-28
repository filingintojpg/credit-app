package com.practice.common.service;

import com.practice.common.model.Application;
import com.practice.common.model.Decision;
import com.practice.common.repository.ApplicationRepository;
import com.practice.common.repository.DecisionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ReadService {

    private final CacheService cacheService;
    private final ApplicationRepository applicationRepository;
    private final DecisionRepository decisionRepository;

    public ReadService(CacheService applicationCacheService,
                       ApplicationRepository applicationRepository,
                       DecisionRepository decisionRepository) {
        this.cacheService = applicationCacheService;
        this.applicationRepository = applicationRepository;
        this.decisionRepository = decisionRepository;
    }

    public Application getApplicationById(Long applicationId) {
        return cacheService.getApplication(applicationId).orElseGet(() -> {
            Application fromDB = applicationRepository.findById(applicationId);
            if (fromDB == null) {
                throw new EntityNotFoundException("Application not found by ID: " + applicationId);
            }
            cacheService.putApplication(fromDB);

            return fromDB;
        });
    }

    public Decision getDecisionByApplicationId(Long applicationId) {
        return cacheService.getDecision(applicationId).orElseGet(() -> {
            Decision fromDB = decisionRepository.findByApplicationId(applicationId);
            if (fromDB == null) {
                throw new EntityNotFoundException("Decision not found by application ID: " + applicationId);
            }
            cacheService.putDecision(fromDB);

            return fromDB;
        });
    }
}
