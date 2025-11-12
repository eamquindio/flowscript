-- Create payments database
CREATE DATABASE payments_db;

\c payments_db;

-- Create payments table
CREATE TABLE IF NOT EXISTS payments (
    id SERIAL PRIMARY KEY,
    card_number VARCHAR(20) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    order_data JSONB,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

-- Create credit_cards table
CREATE TABLE IF NOT EXISTS credit_cards (
    id SERIAL PRIMARY KEY,
    card_number VARCHAR(20) UNIQUE NOT NULL,
    credit_limit DECIMAL(10, 2) NOT NULL,
    available_credit DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

-- Create indexes
CREATE INDEX idx_payments_card_number ON payments(card_number);
CREATE INDEX idx_payments_created_at ON payments(created_at);
CREATE INDEX idx_credit_cards_card_number ON credit_cards(card_number);

-- Insert some sample credit cards
INSERT INTO credit_cards (card_number, credit_limit, available_credit) VALUES
    ('****1234', 50000, 50000),
    ('****5678', 30000, 25000),
    ('****9012', 100000, 80000);

-- Grant permissions
GRANT ALL PRIVILEGES ON DATABASE payments_db TO postgres;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO postgres;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO postgres;
