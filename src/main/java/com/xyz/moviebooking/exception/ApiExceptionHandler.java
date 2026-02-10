package com.xyz.moviebooking.exception;

import com.xyz.moviebooking.dto.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFound(NotFoundException ex) {
        return new ErrorResponse("NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(SeatUnavailableException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse seatUnavailable(SeatUnavailableException ex) {
        return new ErrorResponse("SEAT_UNAVAILABLE", ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse conflict(DataIntegrityViolationException ex) {
        return new ErrorResponse("CONFLICT", "Seat already booked by another request.");
    }

    @ExceptionHandler(PaymentFailedException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ErrorResponse paymentFailed(PaymentFailedException ex) {
        return new ErrorResponse("PAYMENT_FAILED", ex.getMessage());
    }
}
