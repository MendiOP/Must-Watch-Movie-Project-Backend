package org.mircroservice.mustwatchbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.mircroservice.mustwatchbackend.dto.MovieRequestDTO;
import org.mircroservice.mustwatchbackend.dto.MovieResponseDTO;
import org.mircroservice.mustwatchbackend.entity.Movie;
import org.mircroservice.mustwatchbackend.exception.ArgumentMissingException;
import org.mircroservice.mustwatchbackend.exception.MovieNotFoundException;
import org.mircroservice.mustwatchbackend.exception.UserNotFoundException;
import org.mircroservice.mustwatchbackend.repository.MovieRepository;
import org.mircroservice.mustwatchbackend.service.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

  private final MovieRepository movieRepository;
  private final ModelMapper modelMapper;

  @Override
  public List<MovieResponseDTO> getAllMovies(String userName) {
    if (userName == null || userName.isEmpty()) {
      throw new ArgumentMissingException("Username cannot be null or empty");
    }

    List<Movie> movies = movieRepository.findByUserName(userName);

    return movies.stream().map(movie -> modelMapper.map(movie, MovieResponseDTO.class)).toList();
  }

  @Override
  public MovieResponseDTO getMovieById(String id) {
    Movie movie =
        movieRepository
            .findById(id)
            .orElseThrow(() -> new MovieNotFoundException("Movie with id " + id + " not found"));
    return modelMapper.map(movie, MovieResponseDTO.class);
  }

  @Override
  public MovieResponseDTO addMovie(MovieRequestDTO movieRequestDTO, String userName) {
    if (movieRequestDTO == null) {
      throw new ArgumentMissingException("MovieRequestDTO cannot be null");
    }

    if (userName == null || userName.isEmpty()) {
      throw new ArgumentMissingException("Username cannot be null or empty");
    }

    Movie movie = modelMapper.map(movieRequestDTO, Movie.class);
    movie.setUserName(userName);
    movie.setCreatedAt(LocalDateTime.now());
    Movie savedMovie = movieRepository.save(movie);

    return modelMapper.map(savedMovie, MovieResponseDTO.class);
  }

  @Override
  public void deleteMovie(String id) {
    if (!movieRepository.existsById(id)) {
      throw new MovieNotFoundException("Movie with id " + id + " not found");
    }
    movieRepository.deleteById(id);
  }

  @Override
  public List<MovieResponseDTO> searchMoviesByTitle(String title) {
    return movieRepository.findByTitleContainingIgnoreCase(title).stream()
        .map(movie -> modelMapper.map(movie, MovieResponseDTO.class))
        .collect(Collectors.toList());
  }
}
