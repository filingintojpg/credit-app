package com.practice.decision_service.delegate;

import com.practice.common.model.enums.DecisionStatus;
import com.practice.common.repository.DecisionRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UpdateDecisionDelegate {

    private final DecisionRepository decisionRepository;

    public UpdateDecisionDelegate(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

    public void updateDecisionStatus(DelegateExecution execution) {
        Long applicationId = (Long) execution.getVariable("applicationId");
        DecisionStatus status = DecisionStatus.valueOf((String) execution.getVariable("decisionStatus"));
        decisionRepository.updateStatus(applicationId, status);
    }
}
