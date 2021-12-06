package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.service.model.BookingDto;
import com.epam.training.ticketservice.service.model.SeatDto;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingService {

    Optional<BookingDto> create(@NonNull String movieTitle, @NonNull String roomName,
                                @NonNull LocalDateTime startTime, @NonNull List<SeatDto> seatDtoList);

    List<BookingDto> list();

}
