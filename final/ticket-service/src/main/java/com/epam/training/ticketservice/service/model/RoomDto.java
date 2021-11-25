package com.epam.training.ticketservice.service.model;

import lombok.Value;

@Value
public class RoomDto {
    String name;
    int rows;
    int cols;
}
