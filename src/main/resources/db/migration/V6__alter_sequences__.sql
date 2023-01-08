ALTER SEQUENCE countries_id_seq START WITH 1 INCREMENT BY 50;
ALTER SEQUENCE genres_id_seq START WITH 1 INCREMENT BY 50;
ALTER SEQUENCE movies_id_seq START WITH 1 INCREMENT BY 50;

ALTER TABLE movie_country ADD CONSTRAINT fk_movcou_on_country FOREIGN KEY (country_id) REFERENCES countries (id);

ALTER TABLE movie_country ADD CONSTRAINT fk_movcou_on_movie FOREIGN KEY (movie_id) REFERENCES movies (id);

ALTER TABLE movie_genre ADD CONSTRAINT fk_movgen_on_genre FOREIGN KEY (genre_id) REFERENCES genres (id);

ALTER TABLE movie_genre ADD CONSTRAINT fk_movgen_on_movie FOREIGN KEY (movie_id) REFERENCES movies (id);

DROP SEQUENCE movie_country_country_id_seq CASCADE;
DROP SEQUENCE movie_country_movie_id_seq CASCADE;
DROP SEQUENCE movie_genre_genre_id_seq CASCADE;
DROP SEQUENCE movie_genre_movie_id_seq CASCADE;