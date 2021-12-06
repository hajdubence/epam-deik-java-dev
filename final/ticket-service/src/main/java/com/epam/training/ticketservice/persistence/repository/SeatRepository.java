package com.epam.training.ticketservice.persistence.repository;

import com.epam.training.ticketservice.persistence.entity.Screening;
import com.epam.training.ticketservice.persistence.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    Optional<Seat> findByBooking_ScreeningAndRowAndCol(Screening screening, int row, int col);

}