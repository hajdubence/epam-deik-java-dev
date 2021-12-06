package com.epam.training.ticketservice.persistence.repository;

import com.epam.training.ticketservice.persistence.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByAccount_Username(String username);

}