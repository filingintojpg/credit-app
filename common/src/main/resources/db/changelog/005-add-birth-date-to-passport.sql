--liquibase formatted sql

--changeset filingintojpg:10
ALTER TABLE client_passports ADD COLUMN birth_date DATE;
