package com.epam.training.ticketservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking extends BaseEntity {

    @ManyToOne
    Account account;
    @ManyToOne
    Screening screening;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "booking")
    List<Seat> seats;
    int price;
}
