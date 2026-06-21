--liquibase formatted sql

--changeset filingintojpg:11
ALTER TABLE client_applications ALTER COLUMN phone TYPE VARCHAR(15);
