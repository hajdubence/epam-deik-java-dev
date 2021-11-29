package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.persistence.entity.Room;
import com.epam.training.ticketservice.persistence.repository.RoomRepository;
import com.epam.training.ticketservice.service.model.RoomDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RoomServiceImplTest {

    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final RoomServiceImpl underTest = new RoomServiceImpl(roomRepository);

    @Test
    void createShouldReturnOptionalEmptyWhenRoomExists() {
        Optional<RoomDto> expected = Optional.empty();
        Room room = new Room("name",12, 34);
        when(roomRepository.findByName("name")).thenReturn(Optional.of(room));

        Optional<RoomDto> actual = underTest.create("name",12, 34);

        assertEquals(expected, actual);
        verify(roomRepository).findByName("name");
    }

    @Test
    void createShouldReturnOptionalRoomWhenRoomNotExists() {
        RoomDto roomDto = new RoomDto("name",12, 34);
        Optional<RoomDto> expected = Optional.of(roomDto);
        when(roomRepository.findByName("name")).thenReturn(Optional.empty());
        Room room = new Room("name",12, 34);
        when(roomRepository.save(room)).thenReturn(room);

        Optional<RoomDto> actual = underTest.create("name",12, 34);

        assertEquals(expected, actual);
        verify(roomRepository).findByName("name");
        verify(roomRepository).save(room);
    }

    @Test
    void updateShouldReturnOptionalEmptyWhenRoomNotExists() {
        Optional<RoomDto> expected = Optional.empty();
        when(roomRepository.findByName("name")).thenReturn(Optional.empty());

        Optional<RoomDto> actual = underTest.update("name",12, 34);

        assertEquals(expected, actual);
        verify(roomRepository).findByName("name");
    }

    @Test
    void updateShouldReturnOptionalRoomWhenRoomExists() {
        RoomDto roomDto = new RoomDto("name",20, 21);
        Optional<RoomDto> expected = Optional.of(roomDto);
        Room oldRoom = new Room("name", 10, 11);
        when(roomRepository.findByName("name")).thenReturn(Optional.of(oldRoom));
        Room newRoom = new Room("name",20, 21);
        when(roomRepository.save(newRoom)).thenReturn(newRoom);

        Optional<RoomDto> actual = underTest.update("name",20, 21);

        assertEquals(expected, actual);
        verify(roomRepository).findByName("name");
        verify(roomRepository).save(newRoom);
    }


    @Test
    void deleteShouldReturnOptionalEmptyWhenRoomNotExists() {
        Optional<RoomDto> expected = Optional.empty();
        when(roomRepository.findByName("name")).thenReturn(Optional.empty());

        Optional<RoomDto> actual = underTest.delete("name");

        assertEquals(expected, actual);
        verify(roomRepository).findByName("name");
    }

    @Test
    void deleteShouldReturnOptionalRoomWhenRoomExists() {
        RoomDto roomDto = new RoomDto("name",12, 34);
        Optional<RoomDto> expected = Optional.of(roomDto);
        Room room = new Room("name",12, 34);
        when(roomRepository.findByName("name")).thenReturn(Optional.of(room));

        Optional<RoomDto> actual = underTest.delete("name");

        assertEquals(expected, actual);
        verify(roomRepository).findByName("name");
        verify(roomRepository).delete(room);
    }

    @Test
    void listWhenOneRoomExists() {
        RoomDto roomDto = new RoomDto("name",12, 34);
        List<RoomDto> expected = List.of(roomDto);
        Room room = new Room("name",12, 34);
        when(roomRepository.findAll()).thenReturn(List.of(room));

        List<RoomDto> actual = underTest.list();

        assertEquals(expected, actual);
        verify(roomRepository).findAll();
    }
}