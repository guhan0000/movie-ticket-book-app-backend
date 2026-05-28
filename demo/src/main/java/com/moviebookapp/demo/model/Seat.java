package com.moviebookapp.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    private String rowLabel;
    private Integer seatNo;
//    @Enumerated(EnumType.STRING)
//    private String seatType;
    @ManyToOne
    @JoinColumn(name = "screen_id")
    Screen screen;
}
