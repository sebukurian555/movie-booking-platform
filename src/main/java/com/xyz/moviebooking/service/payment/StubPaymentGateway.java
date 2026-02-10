package com.xyz.moviebooking.service.payment;

import org.springframework.stereotype.Component;

@Component
public class StubPaymentGateway implements PaymentGateway {
    @Override
    public boolean charge(String bookingId, double amount) {
        // Interview note: replace with real gateway + webhook callback + idempotency keys
        return true;
    }
}
