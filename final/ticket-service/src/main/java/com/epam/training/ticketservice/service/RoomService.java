package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.service.model.RoomDto;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    Optional<RoomDto> create(@NonNull String name, @NonNull int rows, @NonNull int cols);

    Optional<RoomDto> update(@NonNull String name, @NonNull int rows, @NonNull int cols);

    Optional<RoomDto> delete(@NonNull String name);

    List<RoomDto> list();
}
