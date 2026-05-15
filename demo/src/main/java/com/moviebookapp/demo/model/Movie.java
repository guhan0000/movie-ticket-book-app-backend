package com.moviebookapp.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Entity
@Data
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long movieId;
    private String movieName;
    private String language;
    private  Integer ticketPrice;
    private Integer availableSeats;

}
