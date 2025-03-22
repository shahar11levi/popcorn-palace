CREATE TABLE IF NOT EXISTS movie (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    duration INT,
    rating DECIMAL(3, 1),
    release_year INT
);

CREATE TABLE IF NOT EXISTS showtime (
    id SERIAL PRIMARY KEY,
    price DECIMAL(5, 2) NOT NULL,
    movie_id INT NOT NULL,
    theater VARCHAR(100) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movie(id) ON DELETE CASCADE
);