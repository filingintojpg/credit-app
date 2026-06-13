--liquibase formatted sql

--changeset filingintojpg:9
ALTER TABLE system_decisions RENAME COLUMN decided_at TO updated_at;
