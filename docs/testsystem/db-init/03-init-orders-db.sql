-- Create orders database
CREATE DATABASE orders_db;

\c orders_db;

-- Create orders table
CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    order_number VARCHAR(50) UNIQUE NOT NULL,
    customer_name VARCHAR(200) NOT NULL,
    customer_email VARCHAR(200) NOT NULL,
    card_number VARCHAR(20) NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    payment_id INTEGER,
    shipping_id INTEGER,
    tracking_number VARCHAR(50),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

-- Create order_items table (for detailed products in order)
CREATE TABLE IF NOT EXISTS order_items (
    id SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    product_name VARCHAR(200) NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Create order_logs table (for tracking process steps)
CREATE TABLE IF NOT EXISTS order_logs (
    id SERIAL PRIMARY KEY,
    order_number VARCHAR(50) NOT NULL,
    step VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL,
    message TEXT,
    details JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Create indexes
CREATE INDEX idx_orders_order_number ON orders(order_number);
CREATE INDEX idx_orders_customer_email ON orders(customer_email);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_created_at ON orders(created_at);
CREATE INDEX idx_order_items_order_id ON order_items(order_id);
CREATE INDEX idx_order_logs_order_number ON order_logs(order_number);
CREATE INDEX idx_order_logs_created_at ON order_logs(created_at);

-- Insert some sample orders
INSERT INTO orders (
    order_number, customer_name, customer_email, card_number,
    total_amount, payment_id, shipping_id, tracking_number, status
) VALUES
    ('ORD-2025-0001', 'Juan Perez', 'juan.perez@email.com', '****1234', 175000, 1, 1, 'SHP1000001', 'completed'),
    ('ORD-2025-0002', 'Maria Garcia', 'maria.garcia@email.com', '****5678', 320000, 2, 2, 'SHP1000002', 'completed'),
    ('ORD-2025-0003', 'Carlos Rodriguez', 'carlos.rodriguez@email.com', '****9012', 580000, 3, 3, 'SHP1000003', 'completed');

-- Insert sample order items
INSERT INTO order_items (order_id, product_name, quantity, unit_price, subtotal) VALUES
    (1, 'Libro de Programación', 2, 75000, 150000),
    (1, 'Cuaderno', 5, 5000, 25000),
    (2, 'Mouse Inalámbrico', 1, 120000, 120000),
    (2, 'Teclado Mecánico', 1, 200000, 200000),
    (3, 'Monitor 27 pulgadas', 1, 500000, 500000),
    (3, 'Cable HDMI', 2, 40000, 80000);

-- Grant permissions
GRANT ALL PRIVILEGES ON DATABASE orders_db TO postgres;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO postgres;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO postgres;
