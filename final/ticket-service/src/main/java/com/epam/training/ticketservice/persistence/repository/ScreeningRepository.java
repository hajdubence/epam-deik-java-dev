package com.epam.training.ticketservice.persistence.repository;

import com.epam.training.ticketservice.persistence.entity.Movie;
import com.epam.training.ticketservice.persistence.entity.Room;
import com.epam.training.ticketservice.persistence.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Integer> {

    Optional<Screening> findByMovieAndRoomAndStartTime(Movie movie, Room room, LocalDateTime startTime);

    List<Screening> findByRoom(Room room);

    Optional<Screening> findByMovie_TitleAndRoom_NameAndStartTime(String movieTitle, String roomName,
                                                                  LocalDateTime startTime);
}