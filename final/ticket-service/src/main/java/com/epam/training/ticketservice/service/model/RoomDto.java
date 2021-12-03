package com.epam.training.ticketservice.service.model;

import com.epam.training.ticketservice.persistence.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class RoomDto {

    String name;
    int rows;
    int cols;

    public RoomDto(Room room) {
        name = room.getName();
        rows = room.getRows();
        cols = room.getCols();
    }

}
