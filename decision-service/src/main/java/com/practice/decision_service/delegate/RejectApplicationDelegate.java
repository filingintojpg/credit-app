package com.practice.decision_service.delegate;

import com.practice.decision_service.model.enums.DecisionStatus;
import com.practice.decision_service.repository.DecisionRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component
public class RejectApplicationDelegate {

    private final DecisionRepository decisionRepository;

    public RejectApplicationDelegate(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

    public void execute(DelegateExecution execution) {
        Long applicationId = (Long) execution.getVariable("applicationId");
        decisionRepository.updateStatus(applicationId, DecisionStatus.REJECTED);
    }
}
