package com.xyz.moviebooking.controller;

import com.xyz.moviebooking.dto.BookingRequest;
import com.xyz.moviebooking.dto.BookingResponse;
import com.xyz.moviebooking.entity.Booking;
import com.xyz.moviebooking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse create(@Valid @RequestBody BookingRequest request) {
        return bookingService.createBooking(request);
    }

    @GetMapping("/{bookingId}")
    public Booking get(@PathVariable("bookingId") String bookingId) {
        return bookingService.getBooking(bookingId);
    }

}

