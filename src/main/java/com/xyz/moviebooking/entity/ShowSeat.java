package com.xyz.moviebooking.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "show_seats",
        uniqueConstraints = @UniqueConstraint(name = "uk_show_seat", columnNames = {"show_id", "seat_id"}))
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="show_id", nullable = false)
    private Long showId;

    @Column(name="seat_id", nullable = false)
    private Long seatId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status;

    public Long getId() { return id; }
    public Long getShowId() { return showId; }
    public Long getSeatId() { return seatId; }
    public SeatStatus getStatus() { return status; }

    public void setId(Long id) { this.id = id; }
    public void setShowId(Long showId) { this.showId = showId; }
    public void setSeatId(Long seatId) { this.seatId = seatId; }
    public void setStatus(SeatStatus status) { this.status = status; }
}
