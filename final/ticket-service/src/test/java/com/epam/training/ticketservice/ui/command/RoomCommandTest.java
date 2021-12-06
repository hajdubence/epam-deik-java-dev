package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.model.RoomDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RoomCommandTest {

    private final RoomService roomService = mock(RoomService.class);
    private final RoomCommand underTest = new RoomCommand(roomService);

    @Test
    void createWhenRoomAlreadyExists() {
        String expected = "Room already exists";
        when(roomService.create("name", 12,34)).thenReturn(Optional.empty());

        String actual = underTest.create("name", 12,34);

        assertEquals(expected, actual);
        verify(roomService).create("name", 12,34);
    }

    @Test
    void createWhenRoomNotExists() {
        String expected = "Room created";
        RoomDto roomDto = new RoomDto("name", 12,34);
        when(roomService.create("name", 12,34)).thenReturn(Optional.of(roomDto));

        String actual = underTest.create("name", 12,34);

        assertEquals(expected, actual);
        verify(roomService).create("name", 12,34);
    }

    @Test
    void updateWhenRoomNotExists() {
        String expected = "Room does not exist";
        when(roomService.update("name", 12,34)).thenReturn(Optional.empty());

        String actual = underTest.update("name", 12,34);

        assertEquals(expected, actual);
        verify(roomService).update("name", 12,34);
    }

    @Test
    void updateWhenRoomExists() {
        String expected = "Room updated";
        RoomDto roomDto = new RoomDto("name", 12,34);
        when(roomService.update("name", 12,34)).thenReturn(Optional.of(roomDto));

        String actual = underTest.update("name", 12,34);

        assertEquals(expected, actual);
        verify(roomService).update("name", 12,34);
    }

    @Test
    void deleteWhenRoomNotExists() {
        String expected = "Room does not exist";
        when(roomService.delete("title")).thenReturn(Optional.empty());

        String actual = underTest.delete("title");

        assertEquals(expected, actual);
        verify(roomService).delete("title");
    }

    @Test
    void deleteWhenRoomExists() {
        String expected = "Room deleted";
        RoomDto roomDto = new RoomDto("name", 12,34);
        when(roomService.delete("title")).thenReturn(Optional.of(roomDto));

        String actual = underTest.delete("title");

        assertEquals(expected, actual);
        verify(roomService).delete("title");
    }

    @Test
    void listWhenNoRoomExists() {
        String expected = "There are no rooms at the moment";
        when(roomService.list()).thenReturn(List.of());

        String actual = underTest.list();

        assertEquals(expected, actual);
        verify(roomService).list();
    }

    @Test
    void listWhenOneRoomExists() {
        String expected = "Room name with 408 seats, 12 rows and 34 columns";
        RoomDto roomDto = new RoomDto("name", 12,34);
        when(roomService.list()).thenReturn(List.of(roomDto));

        String actual = underTest.list();

        assertEquals(expected, actual);
        verify(roomService).list();
    }
}