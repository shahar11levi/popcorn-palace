package com.att.tdp.popcorn_palace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.att.tdp.popcorn_palace.dto.ShowtimeDTO;
import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.service.ShowtimeService;

@RestController
@RequestMapping("/showtimes")
public class ShowtimeController {

    @Autowired
    private ShowtimeService showtimeService;
    
    @GetMapping("/{showtimeId}")
    public ResponseEntity<ShowtimeDTO> getShowtime(@PathVariable Long showtimeId) {
        Showtime showtime = showtimeService.getShowtimeById(showtimeId);
        ShowtimeDTO showtimeDTO = new ShowtimeDTO(showtime.getId(), showtime.getPrice(), showtime.getMovie().getId(), showtime.getTheater(), showtime.getStartTime(), showtime.getEndTime());
        return ResponseEntity.ok(showtimeDTO);
    }

    @PostMapping()
    public ResponseEntity<ShowtimeDTO> addShowtime(@RequestBody Showtime showtime) {   
        showtimeService.addShowtime(showtime);
        ShowtimeDTO showtimeDTO = new ShowtimeDTO(showtime.getId(), showtime.getPrice(), showtime.getMovie().getId(), showtime.getTheater(), showtime.getStartTime(), showtime.getEndTime());
        return ResponseEntity.ok(showtimeDTO);
    }

    @PostMapping("/update/{showtimeId}")
    public ResponseEntity<Void> updateShowtime(@PathVariable Long showtimeId, @RequestBody Showtime showtime) {
        showtimeService.updateShowtime(showtimeId, showtime);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{showtimeId}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long showtimeId){
        showtimeService.deleteShowtime(showtimeId);
        return ResponseEntity.ok().build();
    }

}
