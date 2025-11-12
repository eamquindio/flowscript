const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const { Pool } = require('pg');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 3001;

// Middleware
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Database connection
const pool = new Pool({
  host: process.env.DB_HOST || 'localhost',
  port: process.env.DB_PORT || 5432,
  database: process.env.DB_NAME || 'payments_db',
  user: process.env.DB_USER || 'postgres',
  password: process.env.DB_PASSWORD || 'postgres',
});

// Test database connection
pool.connect((err, client, release) => {
  if (err) {
    console.error('Error connecting to the database:', err.stack);
  } else {
    console.log('Database connected successfully');
    release();
  }
});

// Health check endpoint
app.get('/health', (req, res) => {
  res.json({ status: 'ok', service: 'payment-service' });
});

// Register payment endpoint - Simulates failures 10% of the time
app.post('/api/payments/register', async (req, res) => {
  const { cardNumber, amount, currency, orderData } = req.body;

  // Validate input
  if (!cardNumber || !amount || !currency) {
    return res.status(400).json({
      success: false,
      error: 'Missing required fields: cardNumber, amount, currency'
    });
  }

  // Simulate 10% failure rate
  const shouldFail = Math.random() < 0.1;

  if (shouldFail) {
    return res.status(500).json({
      success: false,
      error: 'Payment processing failed - service temporarily unavailable',
      errorCode: 'PAYMENT_FAILED'
    });
  }

  try {
    // Mask card number for security
    const maskedCard = cardNumber.slice(-4).padStart(cardNumber.length, '*');

    // Insert payment record
    const result = await pool.query(
      `INSERT INTO payments (card_number, amount, currency, order_data, status, created_at)
       VALUES ($1, $2, $3, $4, $5, NOW())
       RETURNING id, created_at`,
      [maskedCard, amount, currency, JSON.stringify(orderData || {}), 'completed']
    );

    res.status(201).json({
      success: true,
      paymentId: result.rows[0].id,
      transactionDate: result.rows[0].created_at,
      amount,
      currency,
      cardNumber: maskedCard
    });
  } catch (error) {
    console.error('Database error:', error);
    res.status(500).json({
      success: false,
      error: 'Database error',
      details: error.message
    });
  }
});

// Check credit card capacity endpoint
app.post('/api/payments/check-capacity', async (req, res) => {
  const { cardNumber, requestedAmount } = req.body;

  if (!cardNumber || !requestedAmount) {
    return res.status(400).json({
      success: false,
      error: 'Missing required fields: cardNumber, requestedAmount'
    });
  }

  try {
    // Mask card number
    const maskedCard = cardNumber.slice(-4).padStart(cardNumber.length, '*');

    // Check if card exists in our system
    let cardInfo = await pool.query(
      'SELECT * FROM credit_cards WHERE card_number = $1',
      [maskedCard]
    );

    // If card doesn't exist, create it with a random limit
    if (cardInfo.rows.length === 0) {
      const creditLimit = Math.floor(Math.random() * 50000) + 10000; // Random limit between 10k and 60k
      await pool.query(
        'INSERT INTO credit_cards (card_number, credit_limit, available_credit) VALUES ($1, $2, $3)',
        [maskedCard, creditLimit, creditLimit]
      );
      cardInfo = await pool.query(
        'SELECT * FROM credit_cards WHERE card_number = $1',
        [maskedCard]
      );
    }

    const card = cardInfo.rows[0];
    const hasCapacity = card.available_credit >= requestedAmount;

    res.json({
      success: true,
      cardNumber: maskedCard,
      creditLimit: card.credit_limit,
      availableCredit: card.available_credit,
      requestedAmount,
      hasCapacity,
      message: hasCapacity
        ? 'Card has sufficient capacity'
        : 'Insufficient credit available'
    });
  } catch (error) {
    console.error('Database error:', error);
    res.status(500).json({
      success: false,
      error: 'Database error',
      details: error.message
    });
  }
});

// Get payment history
app.get('/api/payments/history', async (req, res) => {
  try {
    const result = await pool.query(
      'SELECT * FROM payments ORDER BY created_at DESC LIMIT 100'
    );
    res.json({
      success: true,
      payments: result.rows
    });
  } catch (error) {
    console.error('Database error:', error);
    res.status(500).json({
      success: false,
      error: 'Database error',
      details: error.message
    });
  }
});

// Start server
app.listen(PORT, () => {
  console.log(`Payment service running on port ${PORT}`);
});

// Graceful shutdown
process.on('SIGTERM', () => {
  console.log('SIGTERM signal received: closing HTTP server');
  pool.end(() => {
    console.log('Database pool closed');
    process.exit(0);
  });
});
