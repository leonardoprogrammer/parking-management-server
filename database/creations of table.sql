-- Users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Parking lots table
CREATE TABLE parking_lots (
    id SERIAL PRIMARY KEY,
    owner_id INT NOT NULL REFERENCES users(id),
    name VARCHAR(100) NOT NULL,
    address TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Parking spaces table
CREATE TABLE parking_spaces (
    id SERIAL PRIMARY KEY,
    parking_lot_id INT NOT NULL REFERENCES parking_lots(id),
    title VARCHAR(50) NOT NULL,
    is_occupied BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Vehicles table
CREATE TABLE vehicles (
    id SERIAL PRIMARY KEY,
    license_plate VARCHAR(20) NOT NULL,
    model VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Parking transactions table
CREATE TABLE parking_transactions (
    id SERIAL PRIMARY KEY,
    parking_space_id INT NOT NULL REFERENCES parking_spaces(id),
    vehicle_id INT NOT NULL REFERENCES vehicles(id),
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    total_fee NUMERIC(10, 2),
    is_paid BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Permissions table
CREATE TABLE permissions (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id),
    parking_lot_id INT NOT NULL REFERENCES parking_lots(id),
    role VARCHAR(50) NOT NULL, -- e.g., "employee", "manager"
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
