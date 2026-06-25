package com.practice.decision_service.delegate;

import com.practice.common.model.Application;
import com.practice.common.model.Decision;
import com.practice.common.model.enums.DecisionStatus;
import com.practice.common.repository.DecisionRepository;
import com.practice.common.service.CacheService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
@Transactional
public class UpdateDecisionDelegate {

    private final DecisionRepository decisionRepository;
    private final CacheService cacheService;

    public UpdateDecisionDelegate(DecisionRepository decisionRepository,
                                  CacheService cacheService) {
        this.decisionRepository = decisionRepository;
        this.cacheService = cacheService;
    }

    public void updateDecisionStatus(DelegateExecution execution) {
        Long applicationId = (Long) execution.getVariable("applicationId");
        DecisionStatus status = DecisionStatus.valueOf((String) execution.getVariable("decisionStatus"));
        decisionRepository.updateStatus(applicationId, status);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                Decision decision = decisionRepository.findByApplicationId(applicationId);
                cacheService.putDecision(decision);
            }
        });
    }
}
