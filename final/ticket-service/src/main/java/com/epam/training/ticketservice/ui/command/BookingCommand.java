package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.service.BookingService;
import com.epam.training.ticketservice.service.model.BookingDto;
import com.epam.training.ticketservice.service.model.SeatDto;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
public class BookingCommand extends CommandAvailability {

    private final BookingService bookingService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public BookingCommand(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @ShellMethodAvailability("isUser")
    @ShellMethod(key = "book", value = "Creates a booking")
    public String create(String movieTitle, String roomName, String start, String seats) {
        LocalDateTime startTime;
        try {
            startTime = LocalDateTime.parse(start, formatter);
        } catch (DateTimeParseException e) {
            return "Parse fail YYYY-MM-DD hh:mm";
        }
        List<SeatDto> seatDtoList = new ArrayList<>();
        String[] seatsArray = seats.split(" ");
        for (String seat : seatsArray) {
            String[] seatArray = seat.split(",");
            if (seatArray.length != 2) {
                return "Failed to parse: " + seat;
            }
            try {
                SeatDto seatDto = new SeatDto(Integer.parseInt(seatArray[0]), Integer.parseInt(seatArray[1]));
                seatDtoList.add(seatDto);
            } catch (NumberFormatException e) {
                return "Failed to parse: " + seat;
            }
        }
        try {
            Optional<BookingDto> optionalBookingDto = bookingService
                    .create(movieTitle, roomName, startTime, seatDtoList);
            if (optionalBookingDto.isPresent()) {
                BookingDto bookingDto = optionalBookingDto.get();
                return String.format("Seats booked: %s; the price for this booking is %d HUF",
                        bookingDto.getSeats()
                                .stream()
                                .map(seatDto -> String.format("(%d,%d)", seatDto.getRow(), seatDto.getCol()))
                                .collect(Collectors.joining(", ")),
                        bookingDto.getPrice());
            }
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        return "Fail";
    }

}
