package com.att.tdp.popcorn_palace.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.att.tdp.popcorn_palace.model.Booking;
import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.repository.BookingRepository;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;

@SpringBootTest
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private ShowtimeService showtimeService;
    @Autowired
    private MovieService movieService;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        bookingRepository.deleteAll();
        showtimeRepository.deleteAll();
        movieRepository.deleteAll();
    }

    private Movie addMovie() {
        Movie movie = new Movie("testMovie", "Crime", 175, 9.1, 1972);
        movieService.addMovie(movie);
        return movie;
    }

    private Showtime addShowtime(Movie movie) {
        Showtime showtime = new Showtime(10.0, movie, "Theater 1", new Date(), new Date());
        showtimeService.addShowtime(showtime);
        return showtime;
    }

    private Booking addBooking(Showtime showtime, UUID userId) {
        Booking booking = new Booking(showtime, 10, userId);
        bookingService.addBooking(booking);
        return booking;
    }

    @Test
    public void testAddBooking() {
        Movie movie = addMovie();
        Showtime showtime = addShowtime(movie);
        UUID userId = UUID.randomUUID();
        Booking booking = addBooking(showtime, userId);
        Booking savedBooking = bookingRepository.findById(booking.getId()).orElse(null);
        assertThat(savedBooking).isNotNull();
        assertThat(savedBooking.getShowtime()).isEqualTo(showtime);
        assertThat(savedBooking.getSeatNumber()).isEqualTo(10);
        assertThat(savedBooking.getUserId()).isEqualTo(userId);
    }

    @Test
    public void testAddBookingWithSameSeat() {
        Movie movie = addMovie();
        Showtime showtime = addShowtime(movie);
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        Booking firstBooking = addBooking(showtime, userId1);
        Booking secondBooking = new Booking(showtime, firstBooking.getSeatNumber(), userId2);
        
        assertThrows(RuntimeException.class, () -> bookingService.addBooking(secondBooking));
    }

}
