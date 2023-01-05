ALTER TABLE movies ADD UNIQUE (name_ukr);

CREATE TABLE posters(
   id SERIAL PRIMARY KEY,
   movie_name VARCHAR(100),
   picture_path VARCHAR(1000),

   CONSTRAINT FK_Posters_Movies FOREIGN KEY(movie_name) REFERENCES movies(name_ukr)
);
