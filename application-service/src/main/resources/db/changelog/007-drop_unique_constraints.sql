--liquibase formatted sql

--changeset filingintojpg:12
ALTER TABLE client_passports DROP CONSTRAINT uq_passport_series_number;

--changeset filingintojpg:13
ALTER TABLE client_employments DROP CONSTRAINT uq_employment_org_position_date;
