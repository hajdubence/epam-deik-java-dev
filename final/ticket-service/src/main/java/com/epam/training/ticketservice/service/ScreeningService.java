package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.service.model.ScreeningDto;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningService {

    Optional<ScreeningDto> create(@NonNull String movieTitle, @NonNull String roomName,
                                  @NonNull LocalDateTime startTime);

    Optional<ScreeningDto> delete(@NonNull String movieTitle, @NonNull String roomName,
                                  @NonNull LocalDateTime startTime);

    List<ScreeningDto> list();
}
