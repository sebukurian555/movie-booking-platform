package com.xyz.moviebooking.service;

import com.xyz.moviebooking.dto.BookingRequest;
import com.xyz.moviebooking.dto.BookingResponse;
import com.xyz.moviebooking.entity.*;
import com.xyz.moviebooking.exception.NotFoundException;
import com.xyz.moviebooking.exception.PaymentFailedException;
import com.xyz.moviebooking.repository.BookingRepository;
import com.xyz.moviebooking.repository.BookingSeatRepository;
import com.xyz.moviebooking.repository.ShowRepository;
import com.xyz.moviebooking.service.payment.PaymentGateway;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {

    private static final double PRICE_PER_TICKET = 250.0;

    private final ShowRepository showRepository;
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final InventoryService inventoryService;
    private final PaymentGateway paymentGateway;

    public BookingService(ShowRepository showRepository,
                          BookingRepository bookingRepository,
                          BookingSeatRepository bookingSeatRepository,
                          InventoryService inventoryService,
                          PaymentGateway paymentGateway) {
        this.showRepository = showRepository;
        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.inventoryService = inventoryService;
        this.paymentGateway = paymentGateway;
    }

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() -> new NotFoundException("Show not found: " + request.getShowId()));

        List<ShowSeat> reserved = inventoryService.reserveSeats(show.getId(), request.getSeatIds());

        Booking booking = new Booking();
        booking.setShowId(show.getId());
        booking.setCustomerName(request.getCustomerName());
        booking.setCustomerEmail(request.getCustomerEmail());
        booking.setStatus(BookingStatus.PENDING_PAYMENT);
        booking.setTotalAmount(PRICE_PER_TICKET * request.getSeatIds().size());

        booking = bookingRepository.save(booking);

        try {

            for (ShowSeat ss : reserved) {
                BookingSeat bs = new BookingSeat();
                bs.setBookingId(booking.getId());
                bs.setShowSeatId(ss.getId());
                bookingSeatRepository.save(bs);
            }
        } catch (DataIntegrityViolationException ex) {
            // Another booking grabbed it at same time
            inventoryService.release(reserved);
            throw ex;
        }

        boolean paid = paymentGateway.charge(booking.getId(), booking.getTotalAmount());
        if (!paid) {
            booking.setStatus(BookingStatus.FAILED);
            bookingRepository.save(booking);
            inventoryService.release(reserved);
            throw new PaymentFailedException("Payment failed for bookingId=" + booking.getId());
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
        inventoryService.markBooked(reserved);

        List<String> seats = reserved.stream()
                .map(ss -> "seatId=" + ss.getSeatId())
                .collect(java.util.stream.Collectors.toList());


        return new BookingResponse(booking.getId(), booking.getStatus().name(), show.getId(), seats, booking.getTotalAmount());
    }

    public Booking getBooking(String bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found: " + bookingId));
    }
}
