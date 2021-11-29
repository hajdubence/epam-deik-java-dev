package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.persistence.entity.Movie;
import com.epam.training.ticketservice.persistence.entity.Room;
import com.epam.training.ticketservice.persistence.entity.Screening;
import com.epam.training.ticketservice.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.persistence.repository.RoomRepository;
import com.epam.training.ticketservice.persistence.repository.ScreeningRepository;
import com.epam.training.ticketservice.service.model.MovieDto;
import com.epam.training.ticketservice.service.model.RoomDto;
import com.epam.training.ticketservice.service.model.ScreeningDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ScreeningServiceImplTest {

    private final ScreeningRepository screeningRepository = mock(ScreeningRepository.class);
    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final ScreeningServiceImpl underTest = new ScreeningServiceImpl(screeningRepository,
            movieRepository, roomRepository);

    @Test
    void createShouldReturnOptionalEmptyWhenMovieNotExists() {
        Optional<ScreeningDto> expected = Optional.empty();
        Room room = new Room("name", 12, 34);
        when(movieRepository.findByTitle("title")).thenReturn(Optional.empty());
        when(roomRepository.findByName("name")).thenReturn(Optional.of(room));

        Optional<ScreeningDto> actual = underTest.create("title","name", LocalDateTime.of(2020,2, 2,20,20));

        assertEquals(expected, actual);
    }

    @Test
    void createShouldReturnOptionalEmptyWhenRoomNotExists() {
        Optional<ScreeningDto> expected = Optional.empty();
        Movie movie = new Movie("title", "genre", 123);
        when(movieRepository.findByTitle("title")).thenReturn(Optional.of(movie));
        when(roomRepository.findByName("name")).thenReturn(Optional.empty());

        Optional<ScreeningDto> actual = underTest.create("title","name", LocalDateTime.of(2020,2, 2,20,20));

        assertEquals(expected, actual);
    }

    @Test
    void createShouldReturnOptionalScreening() {
        MovieDto movieDto = new MovieDto("title", "genre", 123);
        RoomDto roomDto = new RoomDto("name", 12, 34);
        ScreeningDto screeningDto = new ScreeningDto(movieDto, roomDto, LocalDateTime.of(2020,2, 2,20,20));
        Optional<ScreeningDto> expected = Optional.of(screeningDto);
        Movie movie = new Movie("title", "genre", 123);
        when(movieRepository.findByTitle("title")).thenReturn(Optional.of(movie));
        Room room = new Room("name", 12, 34);
        when(roomRepository.findByName("name")).thenReturn(Optional.of(room));
        Screening screening = new Screening(movie, room, LocalDateTime.of(2020,2, 2,20,20));
        when(screeningRepository.save(screening)).thenReturn(screening);

        Optional<ScreeningDto> actual = underTest.create("title","name", LocalDateTime.of(2020,2, 2,20,20));

        assertEquals(expected, actual);
        verify(movieRepository).findByTitle("title");
        verify(roomRepository).findByName("name");
        verify(screeningRepository).save(screening);
    }

    @Test
    void deleteShouldReturnOptionalEmptyWhenScreeningNotExists() {
        Optional<ScreeningDto> expected = Optional.empty();
        when(screeningRepository.findByMovie_TitleAndRoom_NameAndStartTime("title", "name" ,
                LocalDateTime.of(2020,2, 2,20,20))).thenReturn(Optional.empty());

        Optional<ScreeningDto> actual = underTest.delete("title", "name" , LocalDateTime.of(2020,2, 2,20,20));

        assertEquals(expected, actual);
        verify(screeningRepository).findByMovie_TitleAndRoom_NameAndStartTime("title", "name" ,
                LocalDateTime.of(2020,2, 2,20,20));
    }

    @Test
    void deleteShouldReturnOptionalScreeningWhenScreeningExists() {
        MovieDto movieDto = new MovieDto("title", "genre", 123);
        RoomDto roomDto = new RoomDto("name", 12, 34);
        ScreeningDto screeningDto = new ScreeningDto(movieDto, roomDto, LocalDateTime.of(2020,2, 2,20,20));
        Optional<ScreeningDto> expected = Optional.of(screeningDto);
        Movie movie = new Movie("title", "genre", 123);
        Room room = new Room("name", 12, 34);
        Screening screening = new Screening(movie, room, LocalDateTime.of(2020,2, 2,20,20));
        when(screeningRepository.findByMovie_TitleAndRoom_NameAndStartTime("title", "name" ,
                LocalDateTime.of(2020,2, 2,20,20))).thenReturn(Optional.of(screening));

        Optional<ScreeningDto> actual = underTest.delete("title", "name" , LocalDateTime.of(2020,2, 2,20,20));

        assertEquals(expected, actual);
        verify(screeningRepository).findByMovie_TitleAndRoom_NameAndStartTime("title", "name" ,
                LocalDateTime.of(2020,2, 2,20,20));
    }

    @Test
    void listWhenOneScreeningExists() {
        MovieDto movieDto = new MovieDto("title", "genre", 123);
        RoomDto roomDto = new RoomDto("name", 12, 34);
        ScreeningDto screeningDto = new ScreeningDto(movieDto, roomDto, LocalDateTime.of(2020,2, 2,20,20));
        List<ScreeningDto> expected = List.of(screeningDto);
        Movie movie = new Movie("title", "genre", 123);
        Room room = new Room("name", 12, 34);
        Screening screening = new Screening(movie, room, LocalDateTime.of(2020,2, 2,20,20));
        when(screeningRepository.findAll()).thenReturn(List.of(screening));

        List<ScreeningDto> actual = underTest.list();

        assertEquals(expected, actual);
        verify(screeningRepository).findAll();
    }
}