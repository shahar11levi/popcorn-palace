package com.att.tdp.popcorn_palace.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import com.att.tdp.popcorn_palace.service.MovieService;
import com.att.tdp.popcorn_palace.service.ShowtimeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ShowtimeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ShowtimeService showtimeService;
    @Autowired
    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        showtimeRepository.deleteAll();
        movieRepository.deleteAll();
    }

    private Movie addMovie() {
        Movie movie = new Movie("test movie", "Crime", 175, 9.1, 1972);
        movieService.addMovie(movie);
        return movie;
    }

    private Showtime addShowtime(Movie movie) {
        Showtime showtime = new Showtime(10.0, movie, "Theater 1", new Date(), new Date());
        showtimeService.addShowtime(showtime);
        return showtime;
    }

    @Test
    public void testGetShowtimeById() throws Exception {
        Movie movie = addMovie();
        Showtime showtime = addShowtime(movie);
        this.mockMvc.perform(get("/showtimes/" + showtime.getId())).andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(showtime.getPrice()))
                .andExpect(jsonPath("$.theater").value(showtime.getTheater()))
                .andExpect(jsonPath("$.movie.title").value(movie.getTitle()));
    }

    @Test
    public void testAddShowtime() throws Exception {
        Movie movie = addMovie();
        Showtime showtime = new Showtime(10.0, movie, "Theater 1", new Date(), new Date());
        String showtimeJson = new ObjectMapper().writeValueAsString(showtime);
        this.mockMvc.perform(post("/showtimes")
            .contentType("application/json")
            .content(showtimeJson))
            .andExpect(status().isOk());
        
        Showtime savedShowtime = showtimeRepository.findAll().get(0);
        assertThat(savedShowtime).isNotNull();
        assertThat(savedShowtime.getPrice()).isEqualTo(showtime.getPrice());
        assertThat(savedShowtime.getTheater()).isEqualTo(showtime.getTheater());
        assertThat(savedShowtime.getMovie().getTitle()).isEqualTo(showtime.getMovie().getTitle());
    }

    @Test
    public void testUpdateShowtime() throws Exception {
        Movie movie = addMovie();
        Showtime showtime = addShowtime(movie);
        Showtime updatedShowtime = new Showtime(15.0, movie, "Theater 2", new Date(), new Date());
        String showtimeJson = new ObjectMapper().writeValueAsString(updatedShowtime);
        this.mockMvc.perform(post("/showtimes/update/" + showtime.getId())
            .contentType("application/json")
            .content(showtimeJson))
            .andExpect(status().isOk());
        
        Showtime savedShowtime = showtimeRepository.findAll().get(0);
        assertThat(savedShowtime).isNotNull();
        assertThat(savedShowtime.getPrice()).isEqualTo(updatedShowtime.getPrice());
        assertThat(savedShowtime.getTheater()).isEqualTo(updatedShowtime.getTheater());
        assertThat(savedShowtime.getMovie().getTitle()).isEqualTo(updatedShowtime.getMovie().getTitle());
    }

    @Test
    public void testDeleteShowtime() throws Exception {
        Movie movie = addMovie();
        Showtime showtime = addShowtime(movie);
        this.mockMvc.perform(delete("/showtimes/" + showtime.getId()))
            .andExpect(status().isOk());
        
        assertThat(showtimeRepository.findAll()).isEmpty();
    }

}
