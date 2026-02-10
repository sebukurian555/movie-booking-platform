package com.xyz.moviebooking.dto;

import java.util.List;

public class BookingResponse {
    private String bookingId;
    private String status;
    private Long showId;
    private List<String> seats;
    private Double totalAmount;

    public BookingResponse(String bookingId, String status, Long showId, List<String> seats, Double totalAmount) {
        this.bookingId = bookingId;
        this.status = status;
        this.showId = showId;
        this.seats = seats;
        this.totalAmount = totalAmount;
    }

    public String getBookingId() { return bookingId; }
    public String getStatus() { return status; }
    public Long getShowId() { return showId; }
    public List<String> getSeats() { return seats; }
    public Double getTotalAmount() { return totalAmount; }
}
