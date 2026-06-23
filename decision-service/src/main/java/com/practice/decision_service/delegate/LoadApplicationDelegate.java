package com.practice.decision_service.delegate;

import com.practice.common.model.Application;
import com.practice.common.model.enums.DecisionStatus;
import com.practice.common.repository.ApplicationRepository;
import com.practice.common.repository.DecisionRepository;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Component
@Transactional(readOnly = true)
public class LoadApplicationDelegate {

    private final ApplicationRepository applicationRepository;
    private final DecisionRepository decisionRepository;

    public LoadApplicationDelegate(ApplicationRepository applicationRepository,
                                   DecisionRepository decisionRepository) {
        this.applicationRepository = applicationRepository;
        this.decisionRepository = decisionRepository;
    }

    public void loadApplicationData(DelegateExecution execution) {
        Long applicationId = (Long) execution.getVariable("applicationId");

        Application application = applicationRepository.findById(applicationId);
        if (application == null) {
            throw new BpmnError("APPLICATION_NOT_FOUND", "Application " + applicationId + " not found");
        }

        int age = Period.between(application.getPassport().getBirthDate(), LocalDate.now()).getYears();

        execution.setVariable("amount", application.getMoneyAmount());
        execution.setVariable("term", application.getTerm());
        execution.setVariable("age", age);
    }

    public void loadPendingApplications(DelegateExecution execution) {
        List<Long> pendingIds = decisionRepository.findByStatus(DecisionStatus.PENDING)
                .stream()
                .map(decision -> decision.getApplication().getId())
                .toList();

        execution.setVariable("pendingApplicationIds", pendingIds);
    }
}
