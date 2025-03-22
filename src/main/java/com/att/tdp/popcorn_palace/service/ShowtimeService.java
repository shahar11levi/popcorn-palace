package com.att.tdp.popcorn_palace.service;

import org.springframework.stereotype.Service;

import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;

@Service
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;
    
    public ShowtimeService(ShowtimeRepository showtimeRepository) {
        this.showtimeRepository = showtimeRepository;
    }

    public Showtime getShowtimeById(Long id) {
        Showtime showtime = showtimeRepository.findById(id).orElse(null);
        if (showtime == null)
            throw new RuntimeException("The showtime " + id + " not found");
        return showtime;
    }

    public Showtime addShowtime(Showtime showtime) {
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



}
