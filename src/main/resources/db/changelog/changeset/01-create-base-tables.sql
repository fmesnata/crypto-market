--liquibase formatted sql
--changeset fmesnata:tables-and-data

-- CREATE TABLE Cryptocurrency
-- (
--     crypto_id SERIAL PRIMARY KEY,
--     name      VARCHAR(15) NOT NULL,
--     code      VARCHAR(3)  NOT NULL,
--     price     NUMERIC     NOT NULL,
--     CONSTRAINT unique_name UNIQUE (name),
--     CONSTRAINT unique_code UNIQUE (code)
-- );

CREATE TABLE Account
(
    account_id SERIAL PRIMARY KEY,
    owner      VARCHAR(15) NOT NULL,
    balance    NUMERIC     NOT NULL,
    CONSTRAINT unique_owner UNIQUE (owner)
);

CREATE TABLE Investment
(
    investment_id  SERIAL PRIMARY KEY,
    account_id     INTEGER     NOT NULL,
    cryptocurrency VARCHAR(15) NOT NULL,
    quantity       INTEGER     NOT NULL,
    amount         INTEGER     NOT NULL,
    CONSTRAINT unique_investment UNIQUE (account_id, cryptocurrency)
);

--INSERT INTO Cryptocurrency (crypto_id, name, code, price) VALUES (1, 'Bitcoin', 'BTC', 30);
--INSERT INTO Cryptocurrency (crypto_id, name, code, price) VALUES (2, 'Ethereum', 'ETH', 7);
INSERT INTO Account (account_id, owner, balance)
VALUES (1, 'Farid', 1000);
