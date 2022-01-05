--liquibase formatted sql
--changeset fmesnata:tables-and-data

CREATE TABLE Account
(
    account_id SERIAL PRIMARY KEY,
    owner      VARCHAR(15)   NOT NULL,
    balance    NUMERIC(8, 2) NOT NULL,
    CONSTRAINT unique_owner UNIQUE (owner)
);

CREATE TABLE Investment
(
    investment_id  SERIAL PRIMARY KEY,
    account_id     INTEGER       NOT NULL,
    cryptocurrency VARCHAR(15)   NOT NULL,
    quantity       INTEGER       NOT NULL,
    amount         NUMERIC(8, 2) NOT NULL,
    CONSTRAINT unique_investment UNIQUE (account_id, cryptocurrency)
);

INSERT INTO Account (account_id, owner, balance)
VALUES (1, 'Farid', 100000);
