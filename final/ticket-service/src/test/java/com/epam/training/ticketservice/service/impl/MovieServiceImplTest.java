package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.persistence.entity.Movie;
import com.epam.training.ticketservice.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.service.model.MovieDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MovieServiceImplTest {

    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final MovieServiceImpl underTest = new MovieServiceImpl(movieRepository);

    @Test
    void createShouldReturnOptionalEmptyWhenMovieExists() {
        Optional<MovieDto> expected = Optional.empty();
        Movie movie = new Movie("title","genre", 123);
        when(movieRepository.findByTitle("title")).thenReturn(Optional.of(movie));

        Optional<MovieDto> actual = underTest.create("title","genre", 123);

        assertEquals(expected, actual);
        verify(movieRepository).findByTitle("title");
    }

    @Test
    void createShouldReturnOptionalMovieWhenMovieNotExists() {
        MovieDto movieDto = new MovieDto("title","genre", 123);
        Optional<MovieDto> expected = Optional.of(movieDto);
        when(movieRepository.findByTitle("title")).thenReturn(Optional.empty());
        Movie movie = new Movie("title","genre", 123);
        when(movieRepository.save(movie)).thenReturn(movie);

        Optional<MovieDto> actual = underTest.create("title","genre", 123);

        assertEquals(expected, actual);
        verify(movieRepository).findByTitle("title");
        verify(movieRepository).save(movie);
    }

    @Test
    void updateShouldReturnOptionalEmptyWhenMovieNotExists() {
        Optional<MovieDto> expected = Optional.empty();
        when(movieRepository.findByTitle("title")).thenReturn(Optional.empty());

        Optional<MovieDto> actual = underTest.update("title","genre", 123);

        assertEquals(expected, actual);
        verify(movieRepository).findByTitle("title");
    }

    @Test
    void updateShouldReturnOptionalMovieWhenMovieExists() {
        MovieDto movieDto = new MovieDto("title","newGenre", 2);
        Optional<MovieDto> expected = Optional.of(movieDto);
        Movie oldMovie = new Movie("title","oldGenre", 1);
        when(movieRepository.findByTitle("title")).thenReturn(Optional.of(oldMovie));
        Movie newMovie = new Movie("title","newGenre", 2);
        when(movieRepository.save(newMovie)).thenReturn(newMovie);

        Optional<MovieDto> actual = underTest.update("title","newGenre", 2);

        assertEquals(expected, actual);
        verify(movieRepository).findByTitle("title");
        verify(movieRepository).save(newMovie);
    }


    @Test
    void deleteShouldReturnOptionalEmptyWhenMovieNotExists() {
        Optional<MovieDto> expected = Optional.empty();
        when(movieRepository.findByTitle("title")).thenReturn(Optional.empty());

        Optional<MovieDto> actual = underTest.delete("title");

        assertEquals(expected, actual);
        verify(movieRepository).findByTitle("title");
    }

    @Test
    void deleteShouldReturnOptionalMovieWhenMovieExists() {
        MovieDto movieDto = new MovieDto("title","genre", 123);
        Optional<MovieDto> expected = Optional.of(movieDto);
        Movie movie = new Movie("title","genre", 123);
        when(movieRepository.findByTitle("title")).thenReturn(Optional.of(movie));

        Optional<MovieDto> actual = underTest.delete("title");

        assertEquals(expected, actual);
        verify(movieRepository).findByTitle("title");
        verify(movieRepository).delete(movie);
    }

    @Test
    void listWhenOneMovieExists() {
        MovieDto movieDto = new MovieDto("title","genre", 123);
        List<MovieDto> expected = List.of(movieDto);
        Movie movie = new Movie("title","genre", 123);
        when(movieRepository.findAll()).thenReturn(List.of(movie));

        List<MovieDto> actual = underTest.list();

        assertEquals(expected, actual);
        verify(movieRepository).findAll();
    }
}