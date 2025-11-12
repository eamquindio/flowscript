const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const { Pool } = require('pg');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 3002;

// Middleware
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Database connection
const pool = new Pool({
  host: process.env.DB_HOST || 'localhost',
  port: process.env.DB_PORT || 5432,
  database: process.env.DB_NAME || 'shipping_db',
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

// Shipping rates based on package size and city
const SHIPPING_RATES = {
  small: {
    'Bogota': 10000,
    'Medellin': 15000,
    'Cali': 18000,
    'Barranquilla': 22000,
    'Cartagena': 25000,
    'Bucaramanga': 17000,
    'Pereira': 16000,
    'default': 20000
  },
  medium: {
    'Bogota': 20000,
    'Medellin': 28000,
    'Cali': 32000,
    'Barranquilla': 38000,
    'Cartagena': 42000,
    'Bucaramanga': 30000,
    'Pereira': 29000,
    'default': 35000
  },
  large: {
    'Bogota': 35000,
    'Medellin': 45000,
    'Cali': 50000,
    'Barranquilla': 60000,
    'Cartagena': 65000,
    'Bucaramanga': 48000,
    'Pereira': 47000,
    'default': 55000
  },
  xlarge: {
    'Bogota': 50000,
    'Medellin': 65000,
    'Cali': 75000,
    'Barranquilla': 90000,
    'Cartagena': 95000,
    'Bucaramanga': 70000,
    'Pereira': 68000,
    'default': 80000
  }
};

// Health check endpoint
app.get('/health', (req, res) => {
  res.json({ status: 'ok', service: 'shipping-service' });
});

// Calculate shipping cost endpoint
app.post('/api/shipping/calculate-cost', async (req, res) => {
  const { packageSize, destinationCity, weight } = req.body;

  // Validate input
  if (!packageSize || !destinationCity) {
    return res.status(400).json({
      success: false,
      error: 'Missing required fields: packageSize, destinationCity'
    });
  }

  // Validate package size
  const validSizes = ['small', 'medium', 'large', 'xlarge'];
  if (!validSizes.includes(packageSize.toLowerCase())) {
    return res.status(400).json({
      success: false,
      error: `Invalid package size. Must be one of: ${validSizes.join(', ')}`
    });
  }

  try {
    const sizeRates = SHIPPING_RATES[packageSize.toLowerCase()];
    const baseCost = sizeRates[destinationCity] || sizeRates['default'];

    // Add weight surcharge if provided (100 COP per kg over 5kg)
    let totalCost = baseCost;
    let weightSurcharge = 0;
    if (weight && weight > 5) {
      weightSurcharge = (weight - 5) * 100;
      totalCost += weightSurcharge;
    }

    // Log the calculation
    await pool.query(
      `INSERT INTO shipping_calculations (package_size, destination_city, weight, base_cost, weight_surcharge, total_cost, created_at)
       VALUES ($1, $2, $3, $4, $5, $6, NOW())`,
      [packageSize.toLowerCase(), destinationCity, weight || 0, baseCost, weightSurcharge, totalCost]
    );

    res.json({
      success: true,
      packageSize: packageSize.toLowerCase(),
      destinationCity,
      weight: weight || 0,
      baseCost,
      weightSurcharge,
      totalCost,
      currency: 'COP',
      estimatedDeliveryDays: Math.floor(Math.random() * 5) + 3 // Random 3-7 days
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

// Send package endpoint
app.post('/api/shipping/send-package', async (req, res) => {
  const {
    packageSize,
    destinationCity,
    destinationAddress,
    recipientName,
    recipientPhone,
    senderName,
    senderAddress,
    weight,
    contents
  } = req.body;

  // Validate required fields
  if (!packageSize || !destinationCity || !destinationAddress || !recipientName || !senderName) {
    return res.status(400).json({
      success: false,
      error: 'Missing required fields: packageSize, destinationCity, destinationAddress, recipientName, senderName'
    });
  }

  try {
    // Calculate shipping cost
    const sizeRates = SHIPPING_RATES[packageSize.toLowerCase()];
    const baseCost = sizeRates[destinationCity] || sizeRates['default'];
    let totalCost = baseCost;

    if (weight && weight > 5) {
      totalCost += (weight - 5) * 100;
    }

    // Generate tracking number
    const trackingNumber = `SHP${Date.now()}${Math.floor(Math.random() * 1000)}`;

    // Insert shipment record
    const result = await pool.query(
      `INSERT INTO shipments (
        tracking_number, package_size, destination_city, destination_address,
        recipient_name, recipient_phone, sender_name, sender_address,
        weight, contents, shipping_cost, status, created_at
       )
       VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, NOW())
       RETURNING id, created_at`,
      [
        trackingNumber,
        packageSize.toLowerCase(),
        destinationCity,
        destinationAddress,
        recipientName,
        recipientPhone || '',
        senderName,
        senderAddress || '',
        weight || 0,
        contents || '',
        totalCost,
        'pending'
      ]
    );

    res.status(201).json({
      success: true,
      shipmentId: result.rows[0].id,
      trackingNumber,
      status: 'pending',
      shippingCost: totalCost,
      currency: 'COP',
      estimatedDeliveryDate: new Date(Date.now() + (Math.floor(Math.random() * 5) + 3) * 24 * 60 * 60 * 1000).toISOString().split('T')[0],
      createdAt: result.rows[0].created_at
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

// Track package endpoint
app.get('/api/shipping/track/:trackingNumber', async (req, res) => {
  const { trackingNumber } = req.params;

  try {
    const result = await pool.query(
      'SELECT * FROM shipments WHERE tracking_number = $1',
      [trackingNumber]
    );

    if (result.rows.length === 0) {
      return res.status(404).json({
        success: false,
        error: 'Tracking number not found'
      });
    }

    res.json({
      success: true,
      shipment: result.rows[0]
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

// Get all shipments
app.get('/api/shipping/shipments', async (req, res) => {
  try {
    const result = await pool.query(
      'SELECT * FROM shipments ORDER BY created_at DESC LIMIT 100'
    );
    res.json({
      success: true,
      shipments: result.rows
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

// Get available cities
app.get('/api/shipping/cities', (req, res) => {
  const cities = Object.keys(SHIPPING_RATES.small).filter(city => city !== 'default');
  res.json({
    success: true,
    cities,
    packageSizes: ['small', 'medium', 'large', 'xlarge']
  });
});

// Start server
app.listen(PORT, () => {
  console.log(`Shipping service running on port ${PORT}`);
});

// Graceful shutdown
process.on('SIGTERM', () => {
  console.log('SIGTERM signal received: closing HTTP server');
  pool.end(() => {
    console.log('Database pool closed');
    process.exit(0);
  });
});
