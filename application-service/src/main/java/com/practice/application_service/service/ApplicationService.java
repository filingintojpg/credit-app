package com.practice.application_service.service;

import com.practice.application_service.dto.ApplicationRequest;
import com.practice.application_service.dto.ApplicationResponse;
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

@Service
public class ApplicationService {

    private final PassportRepository passportRepository;
    private final EmploymentRepository employmentRepository;
    private final ApplicationRepository applicationRepository;
    private final DecisionRepository decisionRepository;

    public ApplicationService(PassportRepository passportRepository,
                              EmploymentRepository employmentRepository,
                              ApplicationRepository applicationRepository,
                              DecisionRepository decisionRepository) {
        this.passportRepository = passportRepository;
        this.employmentRepository = employmentRepository;
        this.applicationRepository = applicationRepository;
        this.decisionRepository = decisionRepository;
    }

    public ApplicationResponse createApplication(ApplicationRequest request) {
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

        return new ApplicationResponse(application.getId(), DecisionStatus.PENDING.name());
    }

    public ApplicationResponse getStatus(Long applicationId) {
        Application application = applicationRepository.findById(applicationId);

        if (application == null) {
            throw new IllegalArgumentException("Application not found: " + applicationId);
        }

        Decision decision = decisionRepository.findByApplicationId(applicationId);
        String status = (decision != null)
                ? decision.getStatus().name()
                : DecisionStatus.PENDING.name();

        return new ApplicationResponse(applicationId, status);
    }
}
