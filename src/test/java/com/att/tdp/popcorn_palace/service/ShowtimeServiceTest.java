package com.att.tdp.popcorn_palace.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;

@SpringBootTest
public class ShowtimeServiceTest {
    
    @Autowired
    private ShowtimeService showtimeService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        showtimeRepository.deleteAll();
        movieRepository.deleteAll();
    }

    private Movie addMovie() {
        Movie movie = new Movie("The Godfather", "Crime", 175, 9.1, 1972);
        movieService.addMovie(movie);
        return movie;
    }

    private Showtime addShowtime(Movie movie) {
        Showtime showtime = new Showtime(10.0, movie, "Theater 1", LocalDateTime.now().toString(), LocalDateTime.now().plusMinutes(90).toString());
        showtimeService.addShowtime(showtime);
        return showtime;
    }

    @Test
    public void testAddShowtime() {
        Movie movie = addMovie();
        Showtime showtime = addShowtime(movie);
        Showtime savedShowtime = showtimeRepository.findById(showtime.getId()).orElse(null);
        assertThat(savedShowtime).isNotNull();
        assertThat(savedShowtime.getId()).isNotNull();
        assertThat(savedShowtime.getPrice()).isEqualTo(showtime.getPrice());
        assertThat(savedShowtime.getMovie()).isEqualTo(showtime.getMovie());
        assertThat(savedShowtime.getTheater()).isEqualTo(showtime.getTheater());
    }

    @Test
    public void testAddShowtimeOfNonExistingMovie() {
        Movie movie = new Movie("The Godfather", "Crime", 175, 9.1, 1972);
        Showtime showtime = new Showtime(10.0, movie, "Theater 1", LocalDateTime.now().toString(), LocalDateTime.now().plusMinutes(90).toString());
        assertThrows(RuntimeException.class, () -> showtimeService.addShowtime(showtime));
    }

    @Test
    public void testAddShowtimeWithOverlappingTime() {
        Movie movie = addMovie();
        Showtime showtime = addShowtime(movie);
        LocalDateTime newStartTime = LocalDateTime.parse(showtime.getStartTime()).plusMinutes(30);
        LocalDateTime newEndTime = LocalDateTime.parse(showtime.getEndTime()).minusMinutes(30);
        Showtime overlappingShowtime = new Showtime(15.0, movie, showtime.getTheater(), newStartTime.toString(), newEndTime.toString());
        assertThrows(RuntimeException.class, () -> showtimeService.addShowtime(overlappingShowtime));
    }

    @Test
    public void testDeleteShowtime() {
        Movie movie = addMovie();
        Showtime showtime = addShowtime(movie);
        showtimeService.deleteShowtime(showtime.getId());
        Showtime savedShowtime = showtimeRepository.findById(showtime.getId()).orElse(null);
        assertThat(savedShowtime).isNull();
    }

    @Test
    public void testDeleteShowtimeWithNonExistingId() {
        assertThrows(RuntimeException.class, () -> showtimeService.deleteShowtime(1L));
    }

    @Test
    public void testUpdateShowtime() {
        Movie movie = addMovie();
        Showtime showtime = addShowtime(movie);
        Showtime updatedShowtime = new Showtime(15.0, movie, "Theater 2", LocalDateTime.now().toString(), LocalDateTime.now().plusMinutes(90).toString());
        showtimeService.updateShowtime(showtime.getId(), updatedShowtime);
        Showtime savedShowtime = showtimeRepository.findById(showtime.getId()).orElse(null);
        assertThat(savedShowtime).isNotNull();
        assertThat(savedShowtime.getPrice()).isEqualTo(updatedShowtime.getPrice());
        assertThat(savedShowtime.getMovie()).isEqualTo(updatedShowtime.getMovie());
        assertThat(savedShowtime.getTheater()).isEqualTo(updatedShowtime.getTheater());
    }

    @Test
    public void testUpdateShowtimeWithOverlappingTime() {
        Movie movie = addMovie();
        Showtime showtime = addShowtime(movie);
        LocalDateTime newStartTime = LocalDateTime.parse(showtime.getStartTime()).plusDays(1);
        LocalDateTime newEndTime = LocalDateTime.parse(showtime.getEndTime()).plusDays(1);
        Showtime overlappingShowtime = new Showtime(15.0, movie, showtime.getTheater(), newStartTime.toString(), newEndTime.toString());
        showtimeService.addShowtime(overlappingShowtime);
        Showtime updatedShowtime = new Showtime(15.0, movie, showtime.getTheater(), newStartTime.toString(), newEndTime.toString());
        assertThrows(RuntimeException.class, () -> showtimeService.updateShowtime(showtime.getId(), updatedShowtime));
    }

}
