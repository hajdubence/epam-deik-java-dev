package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.model.MovieDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MovieCommandTest {

    private final MovieService movieService = mock(MovieService.class);
    private final MovieCommand underTest = new MovieCommand(movieService);

    @Test
    void createWhenMovieAlreadyExists() {
        String expected = "Movie already exists";
        when(movieService.create("title", "genre",123)).thenReturn(Optional.empty());

        String actual = underTest.create("title", "genre",123);

        assertEquals(expected, actual);
        verify(movieService).create("title", "genre",123);
    }

    @Test
    void createWhenMovieNotExists() {
        String expected = "Movie created";
        MovieDto movieDto = new MovieDto("title", "genre",123);
        when(movieService.create("title", "genre",123)).thenReturn(Optional.of(movieDto));

        String actual = underTest.create("title", "genre",123);

        assertEquals(expected, actual);
        verify(movieService).create("title", "genre",123);
    }

    @Test
    void updateWhenMovieNotExists() {
        String expected = "Movie does not exist";
        when(movieService.update("title", "genre",123)).thenReturn(Optional.empty());

        String actual = underTest.update("title", "genre",123);

        assertEquals(expected, actual);
        verify(movieService).update("title", "genre",123);
    }

    @Test
    void updateWhenMovieExists() {
        String expected = "Movie updated";
        MovieDto movieDto = new MovieDto("title", "genre",123);
        when(movieService.update("title", "genre",123)).thenReturn(Optional.of(movieDto));

        String actual = underTest.update("title", "genre",123);

        assertEquals(expected, actual);
        verify(movieService).update("title", "genre",123);
    }

    @Test
    void deleteWhenMovieNotExists() {
        String expected = "Movie does not exist";
        when(movieService.delete("title")).thenReturn(Optional.empty());

        String actual = underTest.delete("title");

        assertEquals(expected, actual);
        verify(movieService).delete("title");
    }

    @Test
    void deleteWhenMovieExists() {
        String expected = "Movie deleted";
        MovieDto movieDto = new MovieDto("title", "genre",123);
        when(movieService.delete("title")).thenReturn(Optional.of(movieDto));

        String actual = underTest.delete("title");

        assertEquals(expected, actual);
        verify(movieService).delete("title");
    }

    @Test
    void listWhenNoMovieExists() {
        String expected = "There are no movies at the moment";
        when(movieService.list()).thenReturn(List.of());

        String actual = underTest.list();

        assertEquals(expected, actual);
        verify(movieService).list();
    }

    @Test
    void listWhenOneMovieExists() {
        String expected = "title (genre, 123 minutes)";
        MovieDto movieDto = new MovieDto("title", "genre",123);
        when(movieService.list()).thenReturn(List.of(movieDto));

        String actual = underTest.list();

        assertEquals(expected, actual);
        verify(movieService).list();
    }
}