-- Criação da tabela customers
CREATE TABLE customers
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(255)        NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criação da tabela rooms
CREATE TABLE rooms
(
    id    SERIAL PRIMARY KEY,
    type  VARCHAR(255)   NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- Criação dos enums de status
CREATE TYPE reservation_status AS ENUM ('SCHEDULED', 'IN_USE', 'ABSENCE', 'FINISHED', 'CANCELED');

-- Criação da tabela reservations
CREATE TABLE reservations
(
    id             SERIAL PRIMARY KEY,
    customer_id    INT REFERENCES customers (id),
    room_id        INT REFERENCES rooms (id),
    checkin        DATE               NOT NULL,
    checkout       DATE               NOT NULL,
    status         reservation_status NOT NULL
);