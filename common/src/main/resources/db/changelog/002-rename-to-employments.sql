--liquibase formatted sql

--changeset filingintojpg:5
ALTER TABLE client_employment_records RENAME TO client_employments;

--changeset filingintojpg:6
ALTER TABLE client_applications
DROP CONSTRAINT client_applications_employment_id_fkey;

ALTER TABLE client_applications
    ADD CONSTRAINT client_applications_employment_id_fkey
        FOREIGN KEY (employment_id) REFERENCES client_employments (id);
