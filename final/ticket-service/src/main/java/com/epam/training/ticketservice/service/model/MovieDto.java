package com.epam.training.ticketservice.service.model;

import lombok.Value;

@Value
public class MovieDto {
    String title;
    String genre;
    int length;
}
