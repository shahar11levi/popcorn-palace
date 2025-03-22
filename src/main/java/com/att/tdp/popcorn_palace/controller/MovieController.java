package com.att.tdp.popcorn_palace.controller;

import java.util.List;
import com.att.tdp.popcorn_palace.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

import com.att.tdp.popcorn_palace.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/all")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{movieTitle}")
    public ResponseEntity<Movie> getMovie(@PathVariable String movieTitle) {
        return ResponseEntity.ok(movieService.findByTitle(movieTitle));
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        movieService.addMovie(movie);
        return ResponseEntity.ok(movie);
    }

    @PutMapping("/{movieTitle}")
    public ResponseEntity<Void> updateMovie(@RequestBody Movie movie, @PathVariable String movieTitle) {
        movieService.updateMovie(movie, movieTitle);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{movieTitle}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String movieTitle) {
        movieService.deleteMovie(movieTitle);
        return ResponseEntity.noContent().build();
    }

    


    
}