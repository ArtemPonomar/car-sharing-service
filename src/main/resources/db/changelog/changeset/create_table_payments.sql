CREATE TABLE IF NOT EXISTS payments (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
rental_id BIGINT,
session_id VARCHAR(255),
url VARCHAR(255),
payment_amount DECIMAL(10, 2),
type VARCHAR(255) NOT NULL,
status VARCHAR(255) NOT NULL,
CONSTRAINT fk_payment_rental FOREIGN KEY (rental_id) REFERENCES rental(id)
);

