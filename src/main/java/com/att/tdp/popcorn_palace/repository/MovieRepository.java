package com.att.tdp.popcorn_palace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.att.tdp.popcorn_palace.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
