-- Create shipping database
CREATE DATABASE shipping_db;

\c shipping_db;

-- Create shipping_calculations table
CREATE TABLE IF NOT EXISTS shipping_calculations (
    id SERIAL PRIMARY KEY,
    package_size VARCHAR(20) NOT NULL,
    destination_city VARCHAR(100) NOT NULL,
    weight DECIMAL(10, 2),
    base_cost DECIMAL(10, 2) NOT NULL,
    weight_surcharge DECIMAL(10, 2) DEFAULT 0,
    total_cost DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Create shipments table
CREATE TABLE IF NOT EXISTS shipments (
    id SERIAL PRIMARY KEY,
    tracking_number VARCHAR(50) UNIQUE NOT NULL,
    package_size VARCHAR(20) NOT NULL,
    destination_city VARCHAR(100) NOT NULL,
    destination_address TEXT NOT NULL,
    recipient_name VARCHAR(200) NOT NULL,
    recipient_phone VARCHAR(20),
    sender_name VARCHAR(200) NOT NULL,
    sender_address TEXT,
    weight DECIMAL(10, 2),
    contents TEXT,
    shipping_cost DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    delivered_at TIMESTAMP
);

-- Create indexes
CREATE INDEX idx_shipments_tracking_number ON shipments(tracking_number);
CREATE INDEX idx_shipments_status ON shipments(status);
CREATE INDEX idx_shipments_created_at ON shipments(created_at);
CREATE INDEX idx_shipping_calculations_created_at ON shipping_calculations(created_at);

-- Insert some sample shipments
INSERT INTO shipments (
    tracking_number, package_size, destination_city, destination_address,
    recipient_name, recipient_phone, sender_name, sender_address,
    weight, contents, shipping_cost, status
) VALUES
    ('SHP1000001', 'small', 'Bogota', 'Calle 100 #15-20', 'Juan Perez', '3001234567', 'Maria Lopez', 'Carrera 7 #50-10', 2.5, 'Books', 10000, 'delivered'),
    ('SHP1000002', 'medium', 'Medellin', 'Carrera 43A #1-50', 'Ana Garcia', '3109876543', 'Carlos Rodriguez', 'Avenida El Poblado #10-20', 8.0, 'Electronics', 28000, 'in_transit'),
    ('SHP1000003', 'large', 'Cali', 'Avenida 6N #28-10', 'Luis Martinez', '3201234567', 'Sofia Gonzalez', 'Calle 85 #50-30', 15.0, 'Furniture parts', 50000, 'pending');

-- Grant permissions
GRANT ALL PRIVILEGES ON DATABASE shipping_db TO postgres;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO postgres;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO postgres;
