package org.mircroservice.mustwatchbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.mircroservice.mustwatchbackend.dto.MovieRequestDTO;
import org.mircroservice.mustwatchbackend.dto.MovieResponseDTO;
import org.mircroservice.mustwatchbackend.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieResponseDTO>> getAllMovies(String userName) {
        return ResponseEntity.ok(movieService.getAllMovies(userName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> getMovieById(@PathVariable String id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PostMapping
    public ResponseEntity<MovieResponseDTO> addMovie(@RequestBody MovieRequestDTO dto, String userName) {
        MovieResponseDTO createdMovie = movieService.addMovie(dto, userName);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieResponseDTO>> searchMovies(@RequestParam String title) {
        return ResponseEntity.ok(movieService.searchMoviesByTitle(title));
    }
}