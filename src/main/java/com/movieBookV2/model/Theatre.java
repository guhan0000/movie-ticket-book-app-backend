package com.movieBookV2.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "theatres")
public class Theatre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long theatreId;
    private String theatreName;
    private String city;

}