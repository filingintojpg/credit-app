--liquibase formatted sql

--changeset filingintojpg:1
CREATE TABLE client_passports
(
    id             BIGSERIAL PRIMARY KEY,
    last_name      VARCHAR(100) NOT NULL,
    first_name     VARCHAR(100) NOT NULL,
    middle_name    VARCHAR(100),
    series         VARCHAR(4)   NOT NULL,
    number         VARCHAR(6)   NOT NULL,
    address        VARCHAR(255) NOT NULL,
    marital_status VARCHAR(20)  NOT NULL
);

--changeset filingintojpg:2
CREATE TABLE client_employment_records
(
    id           BIGSERIAL PRIMARY KEY,
    organization VARCHAR(255) NOT NULL,
    position     VARCHAR(255) NOT NULL,
    employed_at  DATE         NOT NULL,
    dismissed_at DATE
);

--changeset filingintojpg:3
CREATE TABLE client_applications
(
    id            BIGSERIAL PRIMARY KEY,
    phone         VARCHAR(20)    NOT NULL,
    money_amount  NUMERIC(10, 2) NOT NULL,
    term          INTEGER        NOT NULL,
    passport_id   BIGINT         NOT NULL REFERENCES client_passports (id),
    employment_id BIGINT         NOT NULL REFERENCES client_employment_records (id)
);

--changeset filingintojpg:4
CREATE TABLE system_decisions
(
    id             BIGSERIAL PRIMARY KEY,
    application_id BIGINT      NOT NULL REFERENCES client_applications (id),
    status         VARCHAR(20) NOT NULL,
    decided_at     TIMESTAMP   NOT NULL
);
