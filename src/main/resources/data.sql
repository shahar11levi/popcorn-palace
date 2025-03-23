
INSERT INTO movie (id ,title, genre, duration, rating, release_year) VALUES
(1, 'The Matrix', 'Sci-Fi', 136, 4, 1999),
(2, 'Inception', 'Sci-Fi', 148, 3.4, 2010),
(3, 'The Godfather', 'Crime', 175, 9.5, 1972),
(4, 'The Dark Knight', 'Action', 152, 7.2, 2008),
(5, 'Pulp Fiction', 'Crime', 154, 2.5, 1994),
(6, 'Forrest Gump', 'Drama', 142, 9.6, 1994),
(7, 'The Shawshank Redemption', 'Drama', 142, 5.6, 1994),
(8, 'Fight Club', 'Drama', 139, 8.9, 1999),
(9, 'The Lord of the Rings: The Fellowship of the Ring', 'Fantasy', 178, 7.4, 2001),
(10, 'Interstellar', 'Sci-Fi', 169, 6, 2014);


INSERT INTO showtime (id, price, movie_id, theater, start_time, end_time) VALUES
(1, 10.00, 1, 'Theater 1', '2021-07-01 19:00:00', '2021-07-01 21:16:00'),
(2, 10.00, 1, 'Theater 2', '2021-07-01 19:00:00', '2021-07-01 21:28:00');

INSERT INTO booking (id, showtime_id, seat_number, user_id) VALUES
('f6a9b422-db5f-4c54-97e1-5b071b61f6e6', 1, 45, '5d909adf-0748-48cd-bb7d-949ea62b4cc2');