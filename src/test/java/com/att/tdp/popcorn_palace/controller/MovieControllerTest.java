package com.att.tdp.popcorn_palace.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        movieRepository.deleteAll();
    }

    @Test
    public void testGetAllMovies() throws Exception {
        this.mockMvc.perform(get("/movies/all")).andExpect(status().isOk());
    }

    @Test
    public void testAddMovie() throws Exception {
        Movie movie = new Movie("The Godfather", "Crime", 175, 9, 1972);
        String movieJson = new ObjectMapper().writeValueAsString(movie);
        
        this.mockMvc.perform(post("/movies")
            .contentType("application/json")
            .content(movieJson))
            .andExpect(status().isOk());

        Movie savedMovie = movieRepository.findByTitle("The Godfather");
        assertThat(savedMovie).isNotNull();
        assertThat(savedMovie.getGenre()).isEqualTo("Crime");
        assertThat(savedMovie.getDuration()).isEqualTo(175);
        assertThat(savedMovie.getRating()).isEqualTo(9);
        assertThat(savedMovie.getRelease_year()).isEqualTo(1972);
    }

    @Test
    public void testUpdateMovie() throws Exception {
        Movie movie = new Movie("The Godfather", "Crime", 175, 9, 1972);
        movieRepository.save(movie);

        Movie updatedMovie = new Movie("The Godfather", "Crime", 175, 9.1, 1972);
        String updatedMovieJson = new ObjectMapper().writeValueAsString(updatedMovie);

        this.mockMvc.perform(post("/movies/update/" + movie.getTitle())
            .contentType("application/json")
            .content(updatedMovieJson))
            .andExpect(status().isOk());

        Movie savedMovie = movieRepository.findByTitle(movie.getTitle());
        assertThat(savedMovie).isNotNull();
        assertThat(savedMovie.getRating()).isEqualTo(9.1);
    }

    @Test
    public void testDeleteMovie() throws Exception {
        Movie movie = new Movie("The Godfather", "Crime", 175, 9, 1972);
        movieRepository.save(movie);

        this.mockMvc.perform(delete("/movies/The Godfather"))
            .andExpect(status().isOk());
    }
    
}
