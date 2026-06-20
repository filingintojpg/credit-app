package com.practice.decision_service.delegate;

import com.practice.common.model.Application;
import com.practice.common.repository.ApplicationRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class LoadApplicationDataDelegate {

    private final ApplicationRepository applicationRepository;

    public LoadApplicationDataDelegate(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public void execute(DelegateExecution execution) {
        Long applicationId = (Long) execution.getVariable("applicationId");

        Application application = applicationRepository.findById(applicationId);
        int age = Period.between(application.getPassport().getBirthDate(), LocalDate.now()).getYears();

        execution.setVariable("amount", application.getMoneyAmount());
        execution.setVariable("term", application.getTerm());
        execution.setVariable("age", age);
    }
}
