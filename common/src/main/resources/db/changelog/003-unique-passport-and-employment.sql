--liquibase formatted sql

--changeset filingintojpg:7
ALTER TABLE client_passports
    ADD CONSTRAINT uq_passport_series_number UNIQUE (series, number);

--changeset filingintojpg:8
ALTER TABLE client_employments
    ADD CONSTRAINT uq_employment_org_position_date UNIQUE (organization, position, employed_at);
