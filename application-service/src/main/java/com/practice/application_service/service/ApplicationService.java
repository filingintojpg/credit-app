package com.practice.application_service.service;

import com.practice.application_service.client.CamundaClient;
import com.practice.application_service.dto.*;
import com.practice.application_service.dto.util.ApplicationFilter;
import com.practice.application_service.dto.util.PagedResponse;
import com.practice.application_service.exception.ApplicationNotFoundException;
import com.practice.application_service.model.Application;
import com.practice.application_service.model.Decision;
import com.practice.application_service.model.Employment;
import com.practice.application_service.model.Passport;
import com.practice.application_service.model.enums.DecisionStatus;
import com.practice.application_service.repository.ApplicationRepository;
import com.practice.application_service.repository.DecisionRepository;
import com.practice.application_service.repository.EmploymentRepository;
import com.practice.application_service.repository.PassportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

    private final PassportRepository passportRepository;
    private final EmploymentRepository employmentRepository;
    private final ApplicationRepository applicationRepository;
    private final DecisionRepository decisionRepository;

    private final CamundaClient camundaClient;

    public ApplicationService(PassportRepository passportRepository,
                              EmploymentRepository employmentRepository,
                              ApplicationRepository applicationRepository,
                              DecisionRepository decisionRepository,
                              CamundaClient camundaClient) {
        this.passportRepository = passportRepository;
        this.employmentRepository = employmentRepository;
        this.applicationRepository = applicationRepository;
        this.decisionRepository = decisionRepository;
        this.camundaClient = camundaClient;
    }

    public ApplicationStatusResponse createApplication(ApplicationRequest request) {
        Passport passport = passportRepository.findBySeriesAndNumber(request.getPassportSeries(), request.getPassportNumber());
        if (passport == null) {
            passport = new Passport();
            passport.setLastName(request.getLastName());
            passport.setFirstName(request.getFirstName());
            passport.setMiddleName(request.getMiddleName());
            passport.setSeries(request.getPassportSeries());
            passport.setNumber(request.getPassportNumber());
            passport.setAddress(request.getAddress());
            passport.setMaritalStatus(request.getMaritalStatus());
            passport.setBirthDate(request.getBirthDate());
            passportRepository.save(passport);
        }

        Employment employment = employmentRepository.findByOrganizationAndPositionAndEmployedAt(request.getOrganization(), request.getPosition(), request.getEmployedAt());
        if (employment == null) {
            employment = new Employment();
            employment.setOrganization(request.getOrganization());
            employment.setPosition(request.getPosition());
            employment.setEmployedAt(request.getEmployedAt());
            employment.setDismissedAt(request.getDismissedAt());
            employmentRepository.save(employment);
        }

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

        camundaClient.startCreditApplicationProcess(application.getId());

        return new ApplicationStatusResponse(application.getId(), decision.getStatus().name());
    }

    public ApplicationStatusResponse getStatus(Long applicationId) {
        Application application = applicationRepository.findById(applicationId);

        if (application == null) {
            throw new ApplicationNotFoundException(applicationId);
        }

        Decision decision = decisionRepository.findByApplicationId(applicationId);
        String status = decision.getStatus().name();

        return new ApplicationStatusResponse(applicationId, status);
    }

    public PagedResponse<ApplicationDetailsResponse> getApplications(ApplicationFilter filter) {
        List<ApplicationDetailsResponse> items = applicationRepository.findWithFilters(filter);
        long total = applicationRepository.countWithFilters(filter);
        return new PagedResponse<>(items, filter.getPage(), filter.getSize(), total);
    }
}
