package com.att.tdp.popcorn_palace.service;

import org.springframework.stereotype.Service;

import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;

import jakarta.transaction.Transactional;

@Service
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;

    private final MovieService movieService;
    
    public ShowtimeService(ShowtimeRepository showtimeRepository, MovieService movieService) {
        this.showtimeRepository = showtimeRepository;
        this.movieService = movieService;
    }

    public Showtime getShowtimeById(Long id) {
        Showtime showtime = showtimeRepository.findById(id).orElse(null);
        if (showtime == null)
            throw new RuntimeException("The showtime " + id + " not found");
        return showtime;
    }

    @Transactional
    public Showtime addShowtime(Showtime showtime) {
        Movie movie = showtime.getMovie();
        if (movie == null || movieService.findByTitle(movie.getTitle()) == null)
            throw new RuntimeException("The movie " + movie.getTitle() + " not found");
        if (showtime.getStartTime().after(showtime.getEndTime()))
            throw new RuntimeException("The start time must be before the end time");
        if (isTheaterOccupied(showtime))
            throw new RuntimeException("The theater is already occupied");
        return showtimeRepository.save(showtime);
    }

    public void deleteShowtime(Long id) {
        Showtime showtime = showtimeRepository.findById(id).orElse(null);
        if (showtime != null)
            showtimeRepository.deleteById(id);
        else
            throw new RuntimeException("The showtime " + id + " not found");
    }

    public void updateShowtime(Long id, Showtime showtime) {
        Showtime showtimeToUpdate = showtimeRepository.findById(id).orElse(null);
        if (showtimeToUpdate != null) {
            showtimeToUpdate.setPrice(showtime.getPrice());
            showtimeToUpdate.setMovie(showtime.getMovie());
            showtimeToUpdate.setTheater(showtime.getTheater());
            showtimeToUpdate.setStartTime(showtime.getStartTime());
            showtimeToUpdate.setEndTime(showtime.getEndTime());
            showtimeRepository.save(showtimeToUpdate);
        } else {
            throw new RuntimeException("The showtime " + id + " not found");
        }
    }

    private boolean isTheaterOccupied(Showtime showtime) {
        for (Showtime s : showtimeRepository.findAll()) {
            if (s.getTheater().equals(showtime.getTheater())) {
                if (! (s.getStartTime().after(showtime.getEndTime()) || s.getEndTime().before(showtime.getStartTime())))
                    return true;
            }
        }
        return false;
    }



}
