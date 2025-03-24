package com.att.tdp.popcorn_palace.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.repository.MovieRepository;

@SpringBootTest
public class MovieServiceTest {
        
    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        movieRepository.deleteAll();
    }

    @Test
    public void testAddMovie() {
        Movie movie = new Movie("The Godfather", "Crime", 175, 9.1, 1972);
        movieService.addMovie(movie);
        Movie savedMovie = movieRepository.findByTitle("The Godfather");
        assertThat(savedMovie).isNotNull();
        assertThat(savedMovie.getGenre()).isEqualTo(movie.getGenre());
        assertThat(savedMovie.getDuration()).isEqualTo(movie.getDuration());
        assertThat(savedMovie.getRating()).isEqualTo(movie.getRating());
        assertThat(savedMovie.getReleaseYear()).isEqualTo(movie.getReleaseYear());
    }

    @Test
    public void testAddMovieWithNullTitle() {
        Movie movie = new Movie(null, "Crime", 175, 9.1, 1972);
        assertThrows(RuntimeException.class, () -> movieService.addMovie(movie));
    }

    @Test
    public void testAddMovieWithTheSameTitle() {
        Movie movie1 = new Movie("The Godfather", "Crime", 175, 9.1, 1972);
        movieService.addMovie(movie1);
        Movie movie2 = new Movie("The Godfather", "Crime", 175, 9.1, 1972);
        assertThrows(RuntimeException.class, () -> movieService.addMovie(movie2));
    }

    @Test
    public void testAddMovieWithIligealRating() {
        Movie movie = new Movie("The Godfather", "Crime", 175, 11, 1972);
        assertThrows(RuntimeException.class, () -> movieService.addMovie(movie));
    }

    @Test
    public void testAddMovieWithNegetiveDuration() {
        Movie movie = new Movie("The Godfather", "Crime", -7, 9.1, 1972);
        assertThrows(RuntimeException.class, () -> movieService.addMovie(movie));
    }

    @Test
    public void testDeleteMovie() {
        Movie movie = new Movie("The Godfather", "Crime", 175, 9, 1972);
        movieService.addMovie(movie);
        movieService.deleteMovie("The Godfather");
        Movie savedMovie = movieRepository.findById(movie.getId()).orElse(null);
        assertThat(savedMovie).isNull();
    }

    @Test
    public void testDeleteMovieWithNonExistingTitle() {
        assertThrows(RuntimeException.class, () -> movieService.deleteMovie("movieTitle"));
    }

    @Test
    public void testUpdateMovie() {
        Movie movie = new Movie("The Godfather", "Crime", 175, 9, 1972);
        movieService.addMovie(movie);
        Movie updatedMovie = new Movie("The Godfather", "Crime", 175, 10, 1972);
        movieService.updateMovie(updatedMovie, "The Godfather");
        Movie savedMovie = movieRepository.findByTitle("The Godfather");
        assertThat(savedMovie).isNotNull();
        assertThat(savedMovie.getGenre()).isEqualTo(updatedMovie.getGenre());
        assertThat(savedMovie.getDuration()).isEqualTo(updatedMovie.getDuration());
        assertThat(savedMovie.getRating()).isEqualTo(updatedMovie.getRating());
        assertThat(savedMovie.getReleaseYear()).isEqualTo(updatedMovie.getReleaseYear());
    }

    @Test
    public void testUpdateMovieWithNonExistingTitle() {
        Movie movie = new Movie("The Godfather", "Crime", 175, 9, 1972);
        assertThrows(RuntimeException.class, () -> movieService.updateMovie(movie, "The Godfather"));
    }

    @Test
    public void testUpdateMovieWithIlegalRating() {
        Movie movie = new Movie("The Godfather", "Crime", 175, 9, 1972);
        movieService.addMovie(movie);
        Movie updatedMovie = new Movie("The Godfather", "Crime", 175, -1, 1972);
        assertThrows(RuntimeException.class, () -> movieService.updateMovie(updatedMovie, "The Godfather"));
    }

    @Test
    public void testUpdateMovieWithNegetiveDuration() {
        Movie movie = new Movie("The Godfather", "Crime", 175, 9, 1972);
        movieService.addMovie(movie);
        Movie updatedMovie = new Movie("The Godfather", "Crime", -7, 9, 1972);
        assertThrows(RuntimeException.class, () -> movieService.updateMovie(updatedMovie, "The Godfather"));
    }
        
}
