package com.practice.application_service.service;

import com.practice.application_service.client.CamundaClient;
import com.practice.application_service.dto.request.GetApplicationRequestDTO;
import com.practice.application_service.dto.request.CreateApplicationRequestDTO;
import com.practice.application_service.dto.response.ApplicationDetailsResponseDTO;
import com.practice.application_service.dto.response.ApplicationStatusResponseDTO;
import com.practice.application_service.dto.util.Paged;
import com.practice.application_service.exception.ApplicationNotFoundException;
import com.practice.application_service.repository.ApplicationFilterRepository;
import com.practice.common.model.Application;
import com.practice.common.model.Decision;
import com.practice.common.model.Employment;
import com.practice.common.model.Passport;
import com.practice.common.model.enums.DecisionStatus;
import com.practice.common.repository.ApplicationRepository;
import com.practice.common.repository.DecisionRepository;
import com.practice.common.repository.EmploymentRepository;
import com.practice.common.repository.PassportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class ApplicationService {

    private final PassportRepository passportRepository;
    private final EmploymentRepository employmentRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationFilterRepository applicationFilterRepository;
    private final DecisionRepository decisionRepository;

    private final CamundaClient camundaClient;

    public ApplicationService(PassportRepository passportRepository,
                              EmploymentRepository employmentRepository,
                              ApplicationRepository applicationRepository,
                              ApplicationFilterRepository applicationFilterRepository,
                              DecisionRepository decisionRepository,
                              CamundaClient camundaClient) {
        this.passportRepository = passportRepository;
        this.employmentRepository = employmentRepository;
        this.applicationRepository = applicationRepository;
        this.applicationFilterRepository = applicationFilterRepository;
        this.decisionRepository = decisionRepository;
        this.camundaClient = camundaClient;
    }

    @Transactional
    public ApplicationStatusResponseDTO createApplication(CreateApplicationRequestDTO request) {
        Passport passport = new Passport();
        passport.setLastName(request.getLastName());
        passport.setFirstName(request.getFirstName());
        passport.setMiddleName(request.getMiddleName());
        passport.setSeries(request.getPassportSeries());
        passport.setNumber(request.getPassportNumber());
        passport.setAddress(request.getAddress());
        passport.setMaritalStatus(request.getMaritalStatus());
        passport.setBirthDate(request.getBirthDate());
        passportRepository.save(passport);

        Employment employment = new Employment();
        employment.setOrganization(request.getOrganization());
        employment.setPosition(request.getPosition());
        employment.setEmployedAt(request.getEmployedAt());
        employment.setDismissedAt(request.getDismissedAt());
        employmentRepository.save(employment);

        Application application = new Application();
        application.setPhone(request.getPhone());
        application.setMoneyAmount(request.getAmount());
        application.setTerm(request.getTerm());
        application.setPassport(passport);
        application.setEmployment(employment);
        applicationRepository.save(application);

        Decision decision = new Decision();
        decision.setApplication(application);
        decision.setStatus(DecisionStatus.PENDING);
        decision.setUpdatedAt(LocalDateTime.now());
        decisionRepository.save(decision);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                camundaClient.startCreditApplicationProcess(application.getId());
            }
        });

        return new ApplicationStatusResponseDTO(application.getId(), decision.getStatus().name());
    }

    public ApplicationStatusResponseDTO getStatus(Long applicationId) {
        Application application = applicationRepository.findById(applicationId);

        if (application == null) {
            throw new ApplicationNotFoundException(applicationId);
        }

        Decision decision = decisionRepository.findByApplicationId(applicationId);
        String status = decision.getStatus().name();

        return new ApplicationStatusResponseDTO(applicationId, status);
    }

    public Paged<ApplicationDetailsResponseDTO> getApplications(GetApplicationRequestDTO filter) {
        return applicationFilterRepository.findWithFilters(filter.getFilter(), filter.getPagination());
    }
}
