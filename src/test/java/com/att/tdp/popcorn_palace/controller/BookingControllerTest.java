package com.att.tdp.popcorn_palace.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.att.tdp.popcorn_palace.model.Booking;
import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.repository.BookingRepository;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import com.att.tdp.popcorn_palace.service.MovieService;
import com.att.tdp.popcorn_palace.service.ShowtimeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ShowtimeService showtimeService;
    @Autowired
    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        showtimeRepository.deleteAll();
        movieRepository.deleteAll();
        bookingRepository.deleteAll();
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
    public void testBooking() throws Exception {
        Movie movie = addMovie();
        Showtime showtime = addShowtime(movie);
        UUID userId = UUID.randomUUID();
        Booking booking = new Booking(showtime, 45, userId);
        String bookingJson = new ObjectMapper().writeValueAsString(booking);
        this.mockMvc.perform(post("/bookings")
            .contentType("application/json")
            .content(bookingJson))
            .andExpect(status().isOk());
        
        Booking savedBooking = bookingRepository.findAll().get(0);
        assertThat(savedBooking.getShowtime().getId()).isEqualTo(showtime.getId());
        assertThat(savedBooking.getSeatNumber()).isEqualTo(booking.getSeatNumber());
        assertThat(savedBooking.getUserId()).isEqualTo(userId);

    }
}
