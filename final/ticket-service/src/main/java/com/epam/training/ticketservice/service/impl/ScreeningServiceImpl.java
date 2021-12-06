package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.persistence.entity.Movie;
import com.epam.training.ticketservice.persistence.entity.Room;
import com.epam.training.ticketservice.persistence.entity.Screening;
import com.epam.training.ticketservice.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.persistence.repository.RoomRepository;
import com.epam.training.ticketservice.persistence.repository.ScreeningRepository;
import com.epam.training.ticketservice.service.ScreeningService;
import com.epam.training.ticketservice.service.model.ScreeningDto;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    public ScreeningServiceImpl(ScreeningRepository screeningRepository,
                                MovieRepository movieRepository, RoomRepository roomRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Optional<ScreeningDto> create(@NonNull String movieTitle, @NonNull String roomName,
                                         @NonNull LocalDateTime startTime) {
        Optional<Movie> optionalMovie = movieRepository.findByTitle(movieTitle);
        if (optionalMovie.isEmpty()) {
            return Optional.empty();
        }
        Optional<Room> optionalRoom = roomRepository.findByName(roomName);
        if (optionalRoom.isEmpty()) {
            return Optional.empty();
        }
        Screening screening = new Screening(optionalMovie.get(), optionalRoom.get(), startTime);
        screeningRepository
                .findByRoom(screening.getRoom())
                .forEach(screening2 -> checkOverlaps(screening, screening2));
        screeningRepository.save(screening);
        return Optional.of(new ScreeningDto(screening));
    }

    @Override
    public Optional<ScreeningDto> delete(@NonNull String movieTitle, @NonNull String roomName,
                                         @NonNull LocalDateTime startTime) {
        Optional<Screening> optionalScreening = screeningRepository
                .findByMovie_TitleAndRoom_NameAndStartTime(movieTitle, roomName, startTime);
        if (optionalScreening.isEmpty()) {
            return Optional.empty();
        }
        screeningRepository.delete(optionalScreening.get());
        return Optional.of(new ScreeningDto(optionalScreening.get()));
    }

    @Override
    public List<ScreeningDto> list() {
        return screeningRepository.findAll()
                .stream()
                .map(ScreeningDto::new)
                .collect(Collectors.toList());
    }

    private void checkOverlaps(Screening s1, Screening s2) {
        if (!(s1.getStartTime().plusMinutes(s1.getMovie().getLength()).compareTo(s2.getStartTime()) <= 0
                || s2.getStartTime().plusMinutes(s2.getMovie().getLength()).compareTo(s1.getStartTime()) <= 0)) {
            throw new IllegalArgumentException("There is an overlapping screening");
        }
        if (!(s1.getStartTime().plusMinutes(s1.getMovie().getLength() + 10).compareTo(s2.getStartTime()) <= 0
                || s2.getStartTime().plusMinutes(s2.getMovie().getLength() + 10).compareTo(s1.getStartTime()) <= 0)) {
            throw new IllegalArgumentException("This would start in the break period"
                    + " after another screening in this room");
        }
    }

}
