package com.movieBookV2.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "screens")
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long screenId;
    private String screenName;
    private Integer totalSeats;
    @ManyToOne
    @JoinColumn(name = "theatre_id")
    Theatre theatre;
}
