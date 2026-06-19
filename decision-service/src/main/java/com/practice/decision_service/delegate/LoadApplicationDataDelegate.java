package com.practice.decision_service.delegate;

import com.practice.decision_service.repository.ApplicationRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
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

        var data = applicationRepository.findApplicationData(applicationId);
        int age = Period.between(data.birthDate(), LocalDate.now()).getYears();

        execution.setVariable("amount", data.amount());
        execution.setVariable("term", data.term());
        execution.setVariable("age", age);
    }
}
