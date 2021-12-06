package com.epam.training.ticketservice.service.model;

import com.epam.training.ticketservice.persistence.entity.Seat;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class SeatDto {

    int row;
    int col;

    public SeatDto(Seat seat) {
        row = seat.getRow();
        col = seat.getCol();
    }

}
