package com.ademas.movies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ademas.movies.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{

}
