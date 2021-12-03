package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.persistence.entity.Movie;
import com.epam.training.ticketservice.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.model.MovieDto;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Optional<MovieDto> create(@NonNull String title, @NonNull String genre, @NonNull int length) {
        if (movieRepository.findByTitle(title).isPresent()) {
            return Optional.empty();
        }
        Movie movie = new Movie(title, genre, length);
        movieRepository.save(movie);
        return Optional.of(new MovieDto(movie));
    }

    @Override
    public Optional<MovieDto> update(@NonNull String title, @NonNull String genre, @NonNull int length) {
        Optional<Movie> optionalMovie = movieRepository.findByTitle(title);
        if (optionalMovie.isEmpty()) {
            return Optional.empty();
        }
        Movie movie = optionalMovie.get();
        movie.setGenre(genre);
        movie.setLength(length);
        movieRepository.save(movie);
        return Optional.of(new MovieDto(movie));
    }

    @Override
    public Optional<MovieDto> delete(@NonNull String title) {
        Optional<Movie> optionalMovie = movieRepository.findByTitle(title);
        if (optionalMovie.isEmpty()) {
            return Optional.empty();
        }
        Movie movie = optionalMovie.get();
        movieRepository.delete(movie);
        return Optional.of(new MovieDto(movie));
    }

    @Override
    public List<MovieDto> list() {
        return movieRepository.findAll()
                .stream()
                .map(MovieDto::new)
                .collect(Collectors.toList());
    }

}
