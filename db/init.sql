-- Create Customers Table (assuming you're managing customer data)
CREATE TABLE IF NOT EXISTS customers (
    customer_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    country VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

-- Create Accounts Table
CREATE TABLE IF NOT EXISTS accounts (
    account_id SERIAL PRIMARY KEY,
    customer_id INTEGER NOT NULL,
    country VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
);

-- Create Balances Table
-- This allows for multiple currency balances per account
CREATE TABLE IF NOT EXISTS balances (
    balance_id SERIAL PRIMARY KEY,
    account_id INTEGER NOT NULL,
    currency VARCHAR(3) NOT NULL,
    available_amount DECIMAL(15,2) NOT NULL DEFAULT 0.00, -- Considering 2 decimal places for currency amounts
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
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
    direction VARCHAR(3) CHECK (direction IN ('IN', 'OUT')), -- Only 'IN' or 'OUT'
    description TEXT NOT NULL,
    balance_after_transaction DECIMAL(15,2) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    CONSTRAINT fk_account_transactions
    FOREIGN KEY(account_id)
    REFERENCES accounts(account_id)
    ON DELETE CASCADE
    );
