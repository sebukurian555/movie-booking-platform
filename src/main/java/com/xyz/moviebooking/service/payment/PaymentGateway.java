package com.xyz.moviebooking.service.payment;

public interface PaymentGateway {
    boolean charge(String bookingId, double amount);
}
