CREATE TABLE movies(
   id SERIAL PRIMARY KEY,
   name_ukr VARCHAR(100),
   name_eng VARCHAR(100),
   year INTEGER,
   description TEXT,
   rating NUMERIC,
   price NUMERIC
);

CREATE TABLE countries(
   id SERIAL PRIMARY KEY,
   name VARCHAR(100)
);

CREATE TABLE genres(
   id SERIAL PRIMARY KEY,
   name VARCHAR(100)
);

CREATE TABLE movie_country(
   movie_id SERIAL not null,
   FOREIGN KEY (movie_id) REFERENCES movies(id),
   country_id SERIAL not null,
   FOREIGN KEY (country_id) REFERENCES countries(id),

   CONSTRAINT PK_Countries_Movies PRIMARY KEY (movie_id, country_id)
);

CREATE TABLE movie_genre(
   movie_id SERIAL not null,
   FOREIGN KEY (movie_id) REFERENCES movies(id),
   genre_id SERIAL not null,
   FOREIGN KEY (genre_id ) REFERENCES genres(id),

   CONSTRAINT PK_Genres_Movies PRIMARY KEY (movie_id, genre_id)
);

