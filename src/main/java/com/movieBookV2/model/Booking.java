package com.movieBookV2.model;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "bookings")
public class Booking {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
    private String bookingRef;
    private Double totalAmount;
    private BookingStatus status;            // "PENDING", "CONFIRMED", "CANCELLED"
    private LocalDateTime bookedAt;
}
