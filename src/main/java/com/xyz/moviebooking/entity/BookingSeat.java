package com.xyz.moviebooking.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "booking_seats",
        uniqueConstraints = @UniqueConstraint(name = "uk_booking_show_seat", columnNames = {"show_seat_id"}))
public class BookingSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="booking_id", nullable = false)
    private String bookingId;

    @Column(name="show_seat_id", nullable = false)
    private Long showSeatId;

    public Long getId() { return id; }
    public String getBookingId() { return bookingId; }
    public Long getShowSeatId() { return showSeatId; }

    public void setId(Long id) { this.id = id; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    public void setShowSeatId(Long showSeatId) { this.showSeatId = showSeatId; }
}
