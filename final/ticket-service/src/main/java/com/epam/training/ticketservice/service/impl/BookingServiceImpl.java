package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.persistence.entity.Account;
import com.epam.training.ticketservice.persistence.entity.Booking;
import com.epam.training.ticketservice.persistence.entity.Screening;
import com.epam.training.ticketservice.persistence.entity.Seat;
import com.epam.training.ticketservice.persistence.repository.AccountRepository;
import com.epam.training.ticketservice.persistence.repository.BookingRepository;
import com.epam.training.ticketservice.persistence.repository.ScreeningRepository;
import com.epam.training.ticketservice.persistence.repository.SeatRepository;
import com.epam.training.ticketservice.service.AccountService;
import com.epam.training.ticketservice.service.BookingService;
import com.epam.training.ticketservice.service.model.BookingDto;
import com.epam.training.ticketservice.service.model.SeatDto;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ScreeningRepository screeningRepository;
    private final AccountRepository accountRepository;
    private final SeatRepository seatRepository;
    private final AccountService accountService;

    public BookingServiceImpl(BookingRepository bookingRepository, ScreeningRepository screeningRepository,
                              AccountRepository accountRepository, SeatRepository seatRepository,
                              AccountService accountService) {
        this.bookingRepository = bookingRepository;
        this.screeningRepository = screeningRepository;
        this.accountRepository = accountRepository;
        this.seatRepository = seatRepository;
        this.accountService = accountService;
    }

    @Override
    public Optional<BookingDto> create(@NonNull String movieTitle, @NonNull String roomName,
                                       @NonNull LocalDateTime startTime, @NonNull List<SeatDto> seatDtoList) {
        Account account = accountRepository.findByUsername(getSignedInUsername()).get();
        Optional<Screening> optionalScreening = screeningRepository
                .findByMovie_TitleAndRoom_NameAndStartTime(movieTitle, roomName, startTime);
        if (optionalScreening.isEmpty()) {
            throw new IllegalArgumentException("No such screening");
        }
        Screening screening = optionalScreening.get();
        for (SeatDto seatDto : seatDtoList) {
            if (screening.getRoom().getRows() < seatDto.getRow() || screening.getRoom().getCols() < seatDto.getCol()) {
                throw new IllegalArgumentException(String.format("Seat (%d,%d) does not exist in this room",
                        seatDto.getRow(), seatDto.getCol()));
            }
            Optional<Seat> optionalSeat = seatRepository
                    .findByBooking_ScreeningAndRowAndCol(screening, seatDto.getRow(), seatDto.getCol());
            if (optionalSeat.isPresent()) {
                throw new IllegalArgumentException(String.format("Seat (%d,%d) is already taken",
                        seatDto.getRow(), seatDto.getCol()));
            }
        }
        List<Seat> seats = new ArrayList<>();
        Booking booking = new Booking(account, screening, seats, 1500 * seatDtoList.size());
        bookingRepository.save(booking);
        seatDtoList.forEach(seatDto ->
                seatRepository.save(new Seat(booking, seatDto.getRow(), seatDto.getCol())));
        return Optional.of(new BookingDto(bookingRepository.findById(booking.getId()).get()));
    }

    @Override
    public List<BookingDto> list() {
        return bookingRepository.findByAccount_Username(getSignedInUsername())
                .stream()
                .map(BookingDto::new)
                .collect(Collectors.toList());
    }

    private String getSignedInUsername() {
        if (accountService.getSignedInAccount().isEmpty()) {
            throw new NullPointerException("You are not signed in");
        }
        return accountService.getSignedInAccount().get().getUsername();
    }

}
