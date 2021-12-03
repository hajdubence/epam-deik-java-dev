package com.epam.training.ticketservice.service.model;

import com.epam.training.ticketservice.persistence.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class MovieDto {

    String title;
    String genre;
    int length;

    public MovieDto(Movie movie) {
        title = movie.getTitle();
        genre = movie.getGenre();
        length = movie.getLength();
    }

}
