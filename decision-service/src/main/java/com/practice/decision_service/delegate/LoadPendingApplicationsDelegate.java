package com.practice.decision_service.delegate;

import com.practice.common.model.enums.DecisionStatus;
import com.practice.common.repository.DecisionRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
public class LoadPendingApplicationsDelegate {

    private final DecisionRepository decisionRepository;

    public LoadPendingApplicationsDelegate(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

    public void execute(DelegateExecution execution) throws Exception {
        List<Long> pendingIds = decisionRepository.findByStatus(DecisionStatus.PENDING)
                .stream()
                .map(decision -> decision.getApplication().getId())
                .toList();

        execution.setVariable("pendingApplicationIds", pendingIds);
    }
}
