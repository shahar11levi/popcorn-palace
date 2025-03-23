package com.att.tdp.popcorn_palace.service;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(savedMovie.getGenre()).isEqualTo("Crime");
        assertThat(savedMovie.getDuration()).isEqualTo(175);
        assertThat(savedMovie.getRating()).isEqualTo(9.1);
        assertThat(savedMovie.getRelease_year()).isEqualTo(1972);
    }

    @Test
    public void testDeleteMovie() {
        Movie movie = new Movie("The Godfather", "Crime", 175, 9, 1972);
        movieService.addMovie(movie);
        movieService.deleteMovie("The Godfather");
        Movie savedMovie = movieRepository.findByTitle("The Godfather");
        assertThat(savedMovie).isNull();
    }

    @Test
    public void testUpdateMovie() {
        Movie movie = new Movie("The Godfather", "Crime", 175, 9, 1972);
        movieService.addMovie(movie);
        Movie updatedMovie = new Movie("The Godfather", "Crime", 175, 10, 1972);
        movieService.updateMovie(updatedMovie, "The Godfather");
        Movie savedMovie = movieRepository.findByTitle("The Godfather");
        assertThat(savedMovie).isNotNull();
        assertThat(savedMovie.getGenre()).isEqualTo("Crime");
        assertThat(savedMovie.getDuration()).isEqualTo(175);
        assertThat(savedMovie.getRating()).isEqualTo(10);
        assertThat(savedMovie.getRelease_year()).isEqualTo(1972);
    }
        
}
