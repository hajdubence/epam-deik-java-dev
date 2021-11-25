package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.persistence.entity.Room;
import com.epam.training.ticketservice.persistence.repository.RoomRepository;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.model.RoomDto;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Optional<RoomDto> create(@NonNull String name, @NonNull int rows, @NonNull int cols) {
        if (roomRepository.findByName(name).isPresent()) {
            return Optional.empty();
        }
        Room room = new Room(name, rows, cols);
        roomRepository.save(room);
        return Optional.of(roomToRoomDto(room));
    }

    @Override
    public Optional<RoomDto> update(@NonNull String name, @NonNull int rows, @NonNull int cols) {
        Optional<Room> optionalRoom = roomRepository.findByName(name);
        if (optionalRoom.isEmpty()) {
            return Optional.empty();
        }
        Room room = optionalRoom.get();
        room.setRows(rows);
        room.setCols(cols);
        roomRepository.save(room);
        return Optional.of(roomToRoomDto(room));
    }

    @Override
    public Optional<RoomDto> delete(@NonNull String name) {
        Optional<Room> optionalRoom = roomRepository.findByName(name);
        if (optionalRoom.isEmpty()) {
            return Optional.empty();
        }
        Room room = optionalRoom.get();
        roomRepository.delete(room);
        return Optional.of(roomToRoomDto(room));
    }

    @Override
    public List<RoomDto> list() {
        return roomRepository.findAll()
                .stream()
                .map(this::roomToRoomDto)
                .collect(Collectors.toList());
    }

    private RoomDto roomToRoomDto(Room room) {
        if (room == null) {
            return null;
        }
        return new RoomDto(room.getName(), room.getRows(), room.getCols());
    }
}
