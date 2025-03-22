CREATE TABLE IF NOT EXISTS task (
                                    description VARCHAR(64) NOT NULL,
    completed   VARCHAR(30) NOT NULL);

CREATE TABLE IF NOT EXISTS movie (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    duration INT,
    rating INT,
    release_year INT
);