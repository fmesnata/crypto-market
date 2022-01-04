CREATE TABLE Cryptocurrency
(
    crypto_id SERIAL PRIMARY KEY,
    name      VARCHAR(15) NOT NULL,
    code      VARCHAR(3)  NOT NULL,
    price     NUMERIC     NOT NULL,
    CONSTRAINT unique_name UNIQUE (name),
    CONSTRAINT unique_code UNIQUE (code)
);

CREATE TABLE Account
(
    account_id SERIAL PRIMARY KEY,
    owner      VARCHAR(15) NOT NULL,
    balance    NUMERIC     NOT NULL,
    CONSTRAINT unique_owner UNIQUE (owner)
);

CREATE TABLE Investment
(
    investment_id SERIAL PRIMARY KEY,
    account_id    INTEGER     NOT NULL,
    crypto_id     VARCHAR(15) NOT NULL,
    quantity      INTEGER     NOT NULL,
    amount        INTEGER     NOT NULL,
    CONSTRAINT unique_investment UNIQUE (account_id, crypto_id),
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES Account (account_id)
);
