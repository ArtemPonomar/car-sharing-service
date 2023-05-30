CREATE TABLE IF NOT EXISTS rental (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
rental_date TIMESTAMP NOT NULL,
return_date TIMESTAMP NOT NULL,
actual_return_date TIMESTAMP,
car_id BIGINT,
user_id BIGINT,
CONSTRAINT fk_rental_car FOREIGN KEY (car_id) REFERENCES cars(id),
    CONSTRAINT fk_rental_user FOREIGN KEY (user_id) REFERENCES users(id)
);
