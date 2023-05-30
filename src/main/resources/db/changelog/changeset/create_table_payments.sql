CREATE TABLE IF NOT EXISTS payments (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
rental_id BIGINT,
sessionId VARCHAR(255),
url VARCHAR(255),
paymentAmount DECIMAL(10, 2),
type VARCHAR(255),
status VARCHAR(255),
isDeleted BOOLEAN,
CONSTRAINT fk_payment_rental FOREIGN KEY (rental_id) REFERENCES rental(id)
);
