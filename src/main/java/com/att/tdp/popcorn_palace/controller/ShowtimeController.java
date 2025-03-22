package com.att.tdp.popcorn_palace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.service.ShowtimeService;

@RestController
@RequestMapping("/showtimes")
public class ShowtimeController {
    @Autowired
    private ShowtimeService showtimeService;
    
    @GetMapping("/{showtimeId}")
    public ResponseEntity<Showtime> getShowtime(@PathVariable Long showtimeId) {
        return ResponseEntity.ok(showtimeService.getShowtimeById(showtimeId));
    }

    @PostMapping()
    public ResponseEntity<Showtime> addShowtime(@RequestBody Showtime showtime)
    {   
        showtimeService.addShowtime(showtime);
        return ResponseEntity.ok(showtime);
    }

    @PostMapping("/update/{showtimeId}")
    public ResponseEntity<Showtime> updateShowtime(@PathVariable Long showtimeId, @RequestBody Showtime showtime)
    {
        showtimeService.updateShowtime(showtimeId, showtime);
        return ResponseEntity.ok(showtime);
    }

    @DeleteMapping("/{showtimeId}")
    public ResponseEntity<Showtime> deleteShowtime(@PathVariable Long showtimeId)
    {
        showtimeService.deleteShowtime(showtimeId);
        return ResponseEntity.ok().build();
    }

    

    
}
