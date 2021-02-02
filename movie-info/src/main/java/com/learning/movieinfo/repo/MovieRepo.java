package com.learning.movieinfo.repo;

import org.springframework.data.repository.CrudRepository;

import com.learning.movieinfo.model.Movie;

public interface MovieRepo extends CrudRepository<Movie, Integer> {
	
}
