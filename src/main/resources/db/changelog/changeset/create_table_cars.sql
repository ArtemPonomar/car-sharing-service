CREATE TABLE IF NOT EXISTS cars (
                      daily_fee decimal(38,2) DEFAULT NULL,
                      inventory int DEFAULT NULL,
                      id bigint NOT NULL AUTO_INCREMENT,
                      brand varchar(255) DEFAULT NULL,
                      car_type varchar(255) DEFAULT NULL,
                      model varchar(255) DEFAULT NULL,
                      PRIMARY KEY (id)
);
