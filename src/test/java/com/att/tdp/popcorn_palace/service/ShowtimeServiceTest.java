package com.att.tdp.popcorn_palace.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        Showtime showtime = new Showtime(10.0, movie, "Theater 1", new Date(), new Date());
        showtimeService.addShowtime(showtime);
        return showtime;
    }

    @Test
    public void testAddShowtime() {
        Movie movie = addMovie();
        Showtime showtime = addShowtime(movie);
        Showtime savedShowtime = showtimeRepository.findById(showtime.getId()).orElse(null);
        assertThat(savedShowtime).isNotNull();
        assertThat(savedShowtime.getPrice()).isEqualTo(10.0);
        assertThat(savedShowtime.getMovie()).isEqualTo(movie);
        assertThat(savedShowtime.getTheater()).isEqualTo("Theater 1");
    }

    @Test
    public void testAddShowtimeWithOverlappingTime() {
        Movie movie = addMovie();
        Showtime showtime = addShowtime(movie);
        Showtime overlappingShowtime = new Showtime(15.0, movie, showtime.getTheater(), showtime.getStartTime(), showtime.getEndTime());
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
    public void testUpdateShowtime() {
        Movie movie = addMovie();
        Showtime showtime = addShowtime(movie);
        Showtime updatedShowtime = new Showtime(15.0, movie, "Theater 2", new Date(), new Date());
        showtimeService.updateShowtime(showtime.getId(), updatedShowtime);
        Showtime savedShowtime = showtimeRepository.findById(showtime.getId()).orElse(null);
        assertThat(savedShowtime).isNotNull();
        assertThat(savedShowtime.getPrice()).isEqualTo(15.0);
        assertThat(savedShowtime.getMovie()).isEqualTo(movie);
        assertThat(savedShowtime.getTheater()).isEqualTo("Theater 2");
    }

}
