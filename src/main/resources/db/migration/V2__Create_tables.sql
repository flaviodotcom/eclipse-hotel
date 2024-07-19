-- Criação da tabela customers
CREATE TABLE IF NOT EXISTS customers
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(255)        NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criação da tabela rooms
CREATE TABLE IF NOT EXISTS rooms
(
    id    SERIAL PRIMARY KEY,
    type  VARCHAR(255)   NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- Criação dos enums de status
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'reservation_status') THEN
CREATE TYPE reservation_status AS ENUM ('SCHEDULED', 'IN_USE', 'ABSENCE', 'FINISHED', 'CANCELED');
END IF;
END$$;

-- Criação da tabela reservations
CREATE TABLE IF NOT EXISTS reservations
(
    id             SERIAL PRIMARY KEY,
    customer_id    INT REFERENCES customers (id),
    room_id        INT REFERENCES rooms (id),
    checkin  DATE               NOT NULL,
    checkout DATE               NOT NULL,
    status         reservation_status NOT NULL
);