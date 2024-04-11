
-- Create Accounts Table
CREATE TABLE IF NOT EXISTS accounts (
    account_id SERIAL PRIMARY KEY,
    customer_id INTEGER NOT NULL,
    country VARCHAR(100) NOT NULL
);

-- Create Balances Table
-- This allows for multiple currency balances per account
CREATE TABLE IF NOT EXISTS balances (
    balance_id SERIAL PRIMARY KEY,
    account_id INTEGER NOT NULL,
    currency VARCHAR(3) NOT NULL,
    available_amount DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    CONSTRAINT fk_account
    FOREIGN KEY(account_id)
    REFERENCES accounts(account_id)
    ON DELETE CASCADE
    );

-- Create Transactions Table
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id SERIAL PRIMARY KEY,
    account_id INTEGER NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    direction VARCHAR(3) CHECK (direction IN ('IN', 'OUT')),
    description TEXT NOT NULL,
    CONSTRAINT fk_account_transactions
    FOREIGN KEY(account_id)
    REFERENCES accounts(account_id)
    ON DELETE CASCADE
    );



INSERT INTO accounts (customer_id, country)
VALUES
    (1, 'USA'),
    (2, 'Canada'),
    (3, 'India');


INSERT INTO balances (account_id, currency, available_amount)
VALUES
    (1, 'EUR', 1000.00),
    (2, 'SEK', 1500.00),
    (3, 'GBP', 76000.00),
    (1, 'USD', 1000.00);

INSERT INTO transactions (account_id, amount, currency, direction, description)
VALUES
    (1, 200.00, 'USD', 'IN', 'Initial deposit'),
    (2, 300.00, 'CAD', 'IN', 'Initial deposit'),
    (3, 12000.00, 'INR', 'IN', 'Initial deposit');
