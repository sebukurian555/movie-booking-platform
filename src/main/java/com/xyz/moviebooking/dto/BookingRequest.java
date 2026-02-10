package com.xyz.moviebooking.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public class BookingRequest {

    @NotNull
    private Long showId;

    @NotEmpty
    private List<Long> seatIds;

    @NotBlank
    private String customerName;

    @Email
    @NotBlank
    private String customerEmail;

    public Long getShowId() { return showId; }
    public List<Long> getSeatIds() { return seatIds; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }

    public void setShowId(Long showId) { this.showId = showId; }
    public void setSeatIds(List<Long> seatIds) { this.seatIds = seatIds; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
}
