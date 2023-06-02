CREATE TABLE IF NOT EXISTS rental (
                        actual_return_date datetime(6) DEFAULT NULL,
                        car_id bigint DEFAULT NULL,
                        id bigint NOT NULL AUTO_INCREMENT,
                        rental_date datetime(6) DEFAULT NULL,
                        return_date datetime(6) DEFAULT NULL,
                        user_id bigint DEFAULT NULL,
                        PRIMARY KEY (id),
                        UNIQUE KEY UK_pks4spvdiatnngw3iu088242p (car_id),
                        KEY FKj1ty59tjbwlmx7p4uotyto7lp (user_id),
                        CONSTRAINT FKj1ty59tjbwlmx7p4uotyto7lp FOREIGN KEY (user_id) REFERENCES users (id),
                        CONSTRAINT FKnuod4mksps5gbsg3v53ysr1hf FOREIGN KEY (car_id) REFERENCES cars (id)
);
