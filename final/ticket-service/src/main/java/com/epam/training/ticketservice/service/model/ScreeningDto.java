package com.epam.training.ticketservice.service.model;

import com.epam.training.ticketservice.persistence.entity.Screening;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class ScreeningDto {

    MovieDto movie;
    RoomDto room;
    LocalDateTime startTime;

    public ScreeningDto(Screening screening) {
        movie = new MovieDto(screening.getMovie());
        room = new RoomDto(screening.getRoom());
        startTime = screening.getStartTime();
    }

}
