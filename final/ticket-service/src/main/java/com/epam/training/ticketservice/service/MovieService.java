package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.service.model.MovieDto;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    Optional<MovieDto> create(@NonNull String title, @NonNull String genre, @NonNull int length);

    Optional<MovieDto> update(@NonNull String title, @NonNull String genre, @NonNull int length);

    Optional<MovieDto> delete(@NonNull String title);

    List<MovieDto> list();
}
