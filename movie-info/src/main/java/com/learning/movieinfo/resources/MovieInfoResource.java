package com.learning.movieinfo.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.learning.movieinfo.model.Movie;
import com.learning.movieinfo.model.MovieSummary;
import com.learning.movieinfo.repo.MovieRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@RestController
@RequestMapping("/movies")
public class MovieInfoResource {
	@Autowired 
	private MovieRepo movieRepo;

	@Value("${api.key}")
	private String apiKey;

	@Autowired
	private RestTemplate restTemplate;

	/*
	 * @RequestMapping("/{movieId}") public
	 * Optional<Movie>getMovieInfo(@PathVariable("movieId")int movieId) { return
	 * movieRepo.findById(movieId); }
	 */
	@RequestMapping("/{movieId}")
	public Movie getMovieInfo(@PathVariable("movieId") int movieId) {
		MovieSummary movieSummary = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" +  apiKey, MovieSummary.class);
		return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());

	}

	@PostMapping(path="/add") // Map ONLY POST Requests
	public @ResponseBody String addNewUser (@RequestParam int movieId ,@RequestParam String name
			, @RequestParam String description) {

		Movie n = new Movie();
		n.setMovieId(movieId);
		n.setName(name);
		n.setDescription(description);;
		movieRepo.save(n);
		return "Saved";
	}
	@GetMapping(path="/all")
	public @ResponseBody List<Movie> getAllMovies() {
		// This returns a JSON or XML with the users
		List<Movie> movieList = new ArrayList<>();
		movieRepo.findAll().forEach(movieList::add);

		return movieList;
	}
}
