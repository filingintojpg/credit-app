package com.practice.decision_service.delegate;

import com.practice.decision_service.model.enums.DecisionStatus;
import com.practice.decision_service.repository.DecisionRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("rejectApplicationDelegate")
public class RejectApplicationDelegate implements JavaDelegate {

    private final DecisionRepository decisionRepository;

    public RejectApplicationDelegate(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

    @Override
    public void execute(DelegateExecution execution) {
        Long applicationId = (Long) execution.getVariable("applicationId");
        decisionRepository.updateStatus(applicationId, DecisionStatus.REJECTED);
    }
}
