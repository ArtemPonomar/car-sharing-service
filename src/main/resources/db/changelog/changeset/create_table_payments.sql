CREATE TABLE IF NOT EXISTS payments (
                          payment_amount decimal(38,2) DEFAULT NULL,
                          id bigint NOT NULL AUTO_INCREMENT,
                          rental_id bigint DEFAULT NULL,
                          session_id varchar(255) DEFAULT NULL,
                          status varchar(255) DEFAULT NULL,
                          type varchar(255) DEFAULT NULL,
                          url longtext,
                          PRIMARY KEY (id),
                          UNIQUE KEY UK_6b9oj6np97hxx2k68lir605ml (rental_id),
                          CONSTRAINT FKsd5h2fdxq0uurg1m3jebdl6xd FOREIGN KEY (rental_id) REFERENCES rental (id)
);
