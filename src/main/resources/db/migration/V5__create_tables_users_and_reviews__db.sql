CREATE TABLE users(
   id SERIAL PRIMARY KEY,
   first_name VARCHAR(100),
   last_name VARCHAR(100),
   email VARCHAR(100),
   password VARCHAR(100)
);

CREATE TABLE reviews(
   id SERIAL PRIMARY KEY,
   movie_name VARCHAR(100),
   user_first_name VARCHAR(100),
   user_last_name VARCHAR(100),
   content TEXT
);

