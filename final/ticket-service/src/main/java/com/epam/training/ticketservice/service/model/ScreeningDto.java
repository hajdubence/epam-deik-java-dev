package com.epam.training.ticketservice.service.model;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ScreeningDto {
    MovieDto movie;
    RoomDto room;
    LocalDateTime startTime;
}
