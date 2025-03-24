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
        return movieRepository.findByTitle(movieTitle);
    }

    public void deleteMovie(String movieTitle) {
        Movie movie = movieRepository.findByTitle(movieTitle);
        if (movie != null)
            movieRepository.deleteById(movie.getId());
        else
            throw new RuntimeException("The movie " + movieTitle+ " not found");
    }

    public void updateMovie(Movie movie, String movieTitle){
        Movie movieToUpdate = movieRepository.findByTitle(movieTitle);
        if (movieToUpdate != null) {
            movieToUpdate.setTitle(movie.getTitle());
            movieToUpdate.setGenre(movie.getGenre());
            movieToUpdate.setDuration(movie.getDuration());
            movieToUpdate.setRating(movie.getRating());
            movieToUpdate.setReleaseYear(movie.getReleaseYear());
            movieRepository.save(movieToUpdate);
        } else {
            throw new RuntimeException("The movie " + movieTitle + " not found");
        }
    }
    
}
