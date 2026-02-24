package com.xyz.moviebooking.service.payment;

import org.springframework.stereotype.Component;

@Component
public class StubPaymentGateway implements PaymentGateway {
    @Override
    public boolean charge(String bookingId, double amount) {
        return true;
    }
}
