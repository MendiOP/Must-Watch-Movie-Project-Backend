package org.mircroservice.mustwatchbackend.service;

import org.mircroservice.mustwatchbackend.dto.MovieRequestDTO;
import org.mircroservice.mustwatchbackend.dto.MovieResponseDTO;
import org.mircroservice.mustwatchbackend.entity.Movie;

import java.util.List;

public interface MovieService {
  List<MovieResponseDTO> getAllMovies();

  MovieResponseDTO getMovieById(String id);

  MovieResponseDTO addMovie(MovieRequestDTO movieRequestDTO);

  void deleteMovie(String id);

  List<MovieResponseDTO> searchMoviesByTitle(String title);
}
