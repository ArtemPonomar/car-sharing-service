CREATE TABLE IF NOT EXISTS payments (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
rental_id BIGINT,
session_id VARCHAR(255),
url VARCHAR(255),
payment_amount DECIMAL(10, 2),
type ENUM('PAYMENT', 'FINE') NOT NULL,
status ENUM('PENDING', 'PAID') NOT NULL,
deleted BOOLEAN,
CONSTRAINT fk_payment_rental FOREIGN KEY (rental_id) REFERENCES rental(id)
);
