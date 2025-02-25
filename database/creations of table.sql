CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    telephone VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);

CREATE TABLE reset_password (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    email VARCHAR(255) NOT NULL,
    sent_email BOOLEAN NOT NULL,
	date_expiration TIMESTAMP NOT NULL,
    reset BOOLEAN NOT NULL,
    date_reset TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);

CREATE TABLE parking (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_creator_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    address TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);

CREATE TABLE parking_employee (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	parking_id UUID NOT NULL REFERENCES parking(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    adder_user_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);

CREATE TABLE employee_permissions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	employee_id UUID NOT NULL REFERENCES parking_employee(id) ON DELETE CASCADE,
    checkin_vehicle BOOLEAN NOT NULL,
    checkout_vehicle BOOLEAN NOT NULL,
    add_employee BOOLEAN NOT NULL,
    change_permissions BOOLEAN NOT NULL,
    edit_parking BOOLEAN NOT NULL,
    date_inc TIMESTAMP NOT NULL DEFAULT NOW(),
    created_at TIMESTAMP,
    updateUser UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE parked_vehicle (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	parking_id UUID NOT NULL REFERENCES parking(id) ON DELETE CASCADE,
    plate VARCHAR(20) NOT NULL,
    model VARCHAR(255) NOT NULL,
    color VARCHAR(50),
    space VARCHAR(50),
    entry_date TIMESTAMP NOT NULL,
    checkin_employee_id UUID NOT NULL REFERENCES users(id) ON DELETE SET NULL,
    checkout_date TIMESTAMP,
    checkout_employee_id UUID REFERENCES users(id) ON DELETE SET NULL,
    paid BOOLEAN NOT NULL,
    payment_method VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);
