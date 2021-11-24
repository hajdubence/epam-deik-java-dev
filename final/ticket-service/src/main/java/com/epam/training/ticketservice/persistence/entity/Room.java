package com.epam.training.ticketservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room extends BaseEntity {

    @Column(unique = true)
    private String name;
    private int rows;
    private int cols;
    @OneToMany
    private Set<Screening> screenings;
}