package com.epam.training.ticketservice.service.model;

import com.epam.training.ticketservice.persistence.entity.Account;
import lombok.Value;

@Value
public class MovieDto {
    String title;
    String genre;
    int length;
}
