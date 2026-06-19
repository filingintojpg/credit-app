package com.practice.decision_service.delegate;

import com.practice.decision_service.model.enums.DecisionStatus;
import com.practice.decision_service.repository.DecisionRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component
public class UpdateDecisionDelegate {

    private final DecisionRepository decisionRepository;

    public UpdateDecisionDelegate(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

    public void execute(DelegateExecution execution) {
        Long applicationId = (Long) execution.getVariable("applicationId");
        DecisionStatus status = DecisionStatus.valueOf((String) execution.getVariable("decisionStatus"));
        decisionRepository.updateStatus(applicationId, status);
    }
}
