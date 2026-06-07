package com.movieBookV2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "shows")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long showId;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;
    private LocalDate showDate;
    private LocalTime showTime;
    private String format;
    private String language;
    @Enumerated(EnumType.STRING)
    private ShowStatus status;
    
}
