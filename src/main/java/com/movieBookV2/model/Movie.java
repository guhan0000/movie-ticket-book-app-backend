package com.movieBookV2.model;

import jakarta.persistence.*;
//import jakarta.validation.constraints.Min;
import lombok.Data;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long movieId;
    @Column(name = "movie_name",nullable = false,unique = true)
    private String movieName;
    private String language;
    private Integer duration;
    private String posterUrl;
    @ElementCollection
    private List<String> genre;
    private String description;
    private LocalDate releaseDate;
    private Double rating;




}