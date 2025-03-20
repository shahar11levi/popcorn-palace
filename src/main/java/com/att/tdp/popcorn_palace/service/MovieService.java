package com.att.tdp.popcorn_palace.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.repository.MovieRepository;


@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie findByTitle(String movieTitle) {
        return null;
    }

    public void deleteMovie(Movie movie) {
        if (movieRepository.existsById(movie.getId()))
            movieRepository.deleteById(movie.getId());
        else
            throw new RuntimeException("Movie with id " + movie.getId()  + " not found");
    }

    public void updateMovie(Movie movie, String movieTitle){
        
    }

}
