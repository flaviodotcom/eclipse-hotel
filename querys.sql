-- 1. Quantos clientes temos na base?
SELECT COUNT(*) AS total_clientes
FROM customers;

-- 2. Quantos quartos temos cadastrados?
SELECT COUNT(*) AS total_quartos
FROM rooms;

-- 3. Quantas reservas em aberto o hotel possui no momento?
SELECT COUNT(*) AS reservas_abertas
FROM reservations
WHERE status IN ('SCHEDULED', 'IN_USE');

-- 4. Quantos quartos temos vagos no momento?
SELECT COUNT(*) AS quartos_abertos
FROM rooms
WHERE id NOT IN (
    SELECT DISTINCT room_id
    FROM reservations
    WHERE status IN ('SCHEDULED', 'IN_USE')
      AND CURRENT_DATE BETWEEN checkin AND checkout
);

-- 5. Quantos quartos temos ocupados no momento?
SELECT COUNT(DISTINCT room_id) AS quartos_ocupados
FROM reservations
WHERE status IN ('SCHEDULED', 'IN_USE')
  AND CURRENT_DATE BETWEEN checkin AND checkout;

-- 6. Quantas reservas futuras o hotel possui?
SELECT COUNT(*) AS reservas_futuras
FROM reservations
WHERE checkin > CURRENT_DATE;

-- 7. Qual o quarto mais caro do hotel?
SELECT *
FROM rooms
WHERE price = (SELECT MAX(price) FROM rooms);

-- 8. Qual o quarto com maior histórico de cancelamentos?
SELECT room_id, COUNT(*) AS quarto_mais_cancelado
FROM reservations
WHERE status = 'CANCELED'
GROUP BY room_id
ORDER BY quarto_mais_cancelado DESC
    LIMIT 1;

-- 9. Liste todos os quartos e a quantidade de clientes que já ocuparam cada um.
SELECT r.id AS room_id, COUNT(DISTINCT res.customer_id) AS num_clientes
FROM rooms r
         LEFT JOIN reservations res ON r.id = res.room_id
GROUP BY r.id;

-- 10. Quais são os 3 quartos que possuem um histórico maior de ocupações?
SELECT room_id, COUNT(*) AS occupation_count
FROM reservations
WHERE status IN ('SCHEDULED', 'IN_USE')
GROUP BY room_id
ORDER BY occupation_count DESC
    LIMIT 3;

-- 11. No próximo mês, o hotel fará uma promoção para os seus 10 clientes que possuírem
-- maior histórico de reservas e você foi acionado pelo seu time para extrair esta
-- informação do banco de dados. Quem são os 10 clientes?
SELECT c.id, c.name, COUNT(*) AS reservation_count
FROM customers c
         JOIN reservations r ON c.id = r.customer_id
WHERE r.checkin >= DATE_TRUNC('month', CURRENT_DATE + INTERVAL '1 month')
  AND r.checkin < DATE_TRUNC('month', CURRENT_DATE + INTERVAL '2 month')
GROUP BY c.id, c.name
ORDER BY reservation_count DESC
    LIMIT 10;
