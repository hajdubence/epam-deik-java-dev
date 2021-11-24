package com.epam.training.ticketservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Screening extends BaseEntity {

    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Room room;
    @DateTimeFormat(pattern = "YYYY-MM-DD hh:mm")
    private LocalDateTime startTime;
}