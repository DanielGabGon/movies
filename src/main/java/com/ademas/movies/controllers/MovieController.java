package com.ademas.movies.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ademas.movies.models.Movie;
import com.ademas.movies.repositories.MovieRepository;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
	
	@Autowired
	private MovieRepository movieRepository;
	
	/*
	 * Listar todos los elementos
	 */
	@CrossOrigin
	@GetMapping("/all")
	public List<Movie> getAllMovies(){
		return movieRepository.findAll();
	}
	
	
	/*
	 * Listar un elemento en concreto
	 */
	@CrossOrigin
	@GetMapping("/{id}")
	public ResponseEntity<Movie> getMovieById (@PathVariable Long id) {
		Optional<Movie> movie= movieRepository.findById(id);
		return movie.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
		}
	
	/*
	 * Crear un elemento
	 */
	@CrossOrigin
	@PostMapping("/create")
	public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
		   Movie saveMovie = movieRepository.save(movie);
		return ResponseEntity.status(HttpStatus.CREATED).body(saveMovie);
	}

	/*
	 * Borrar un elemento
	 */
	@CrossOrigin
	@DeleteMapping("/{id}")
	public ResponseEntity<Void>  deleteMovie(@PathVariable Long id){
		if(movieRepository.existsById(id)){
			return ResponseEntity.notFound().build();
		}
		movieRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	/*
	 * Actualizar un elemento
	 */
	@CrossOrigin
	@PutMapping("/{id}")
	public ResponseEntity<Movie> updateMovie(@PathVariable  Long id, @RequestBody Movie updateMovie){
		if(!movieRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		updateMovie.setId(id);
		Movie savedMovie=movieRepository.save(updateMovie);
		return ResponseEntity.ok(savedMovie);
	}
	
	@CrossOrigin
	@GetMapping("/vote/{id}/{rating}")
	public ResponseEntity<Movie> voteMovie(@PathVariable Long id,@PathVariable double rating){
		if(!movieRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		Optional<Movie> optional= movieRepository.findById(id);
		Movie  movie= optional.get();
	    //movie.rating
		//movie.votes
        double 	newRating=((movie.getVotes()*movie.getRating())+rating)/(movie.getVotes()+1);
		  movie.setVotes(movie.getVotes()+1);
		  movie.setRating(newRating);
		  Movie savedMovie= movieRepository.save(movie);
		return ResponseEntity.ok(savedMovie);
	}
	
	
	
}
