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
public class Movie extends BaseEntity {

    @Column(unique = true)
    private String title;
    private String genre;
    private int length;
    @OneToMany
    private Set<Screening> screenings;
}
