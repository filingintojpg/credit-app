package com.practice.decision_service.delegate;

import com.practice.decision_service.repository.ApplicationRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Component("validateApplicationDelegate")
public class ValidateApplicationDelegate implements JavaDelegate {

    private final ApplicationRepository applicationRepository;

    public ValidateApplicationDelegate(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public void execute(DelegateExecution execution) {
        Long applicationId = (Long) execution.getVariable("applicationId");

        var data = applicationRepository.findApplicationData(applicationId);

        BigDecimal amount = data.amount();
        Integer term = data.term();
        int age = Period.between(data.birthDate(), LocalDate.now()).getYears();

        boolean approved =
                amount.compareTo(BigDecimal.valueOf(1000)) >= 0 &&
                        amount.compareTo(BigDecimal.valueOf(30000)) <= 0 &&
                        term >= 3 &&
                        term <= 30 &&
                        age >= 18 &&
                        age <= 60;

        execution.setVariable("approved", approved);
    }
}
