package com.moviebookapp.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long bookingId;
    @Column(name = "booking_code" ,length = 10, nullable = false,unique = true)
    private String bookingCode;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;
    private Double totalAmount;
    private String status;
    private LocalDateTime bookedAt;

}
