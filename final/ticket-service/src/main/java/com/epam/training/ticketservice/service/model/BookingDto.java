package com.epam.training.ticketservice.service.model;

import com.epam.training.ticketservice.persistence.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
@AllArgsConstructor
public class BookingDto {

    ScreeningDto screening;
    List<SeatDto> seats;
    int price;

    public BookingDto(Booking booking) {
        screening = new ScreeningDto(booking.getScreening());
        seats = booking.getSeats()
                .stream()
                .map(SeatDto::new)
                .collect(Collectors.toList());
        price = booking.getPrice();
    }

}
