package com.xyz.moviebooking.service;

import com.xyz.moviebooking.dto.BookingRequest;
import com.xyz.moviebooking.dto.BookingResponse;
import com.xyz.moviebooking.entity.Show;
import com.xyz.moviebooking.repository.BookingRepository;
import com.xyz.moviebooking.repository.BookingSeatRepository;
import com.xyz.moviebooking.repository.ShowRepository;
import com.xyz.moviebooking.service.payment.PaymentGateway;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

public class BookingServiceTest {

    @Test
    public void createBooking_showNotFound_shouldThrow() {
        ShowRepository showRepo = Mockito.mock(ShowRepository.class);
        BookingRepository bookingRepo = Mockito.mock(BookingRepository.class);
        BookingSeatRepository bookingSeatRepo = Mockito.mock(BookingSeatRepository.class);
        InventoryService inventoryService = Mockito.mock(InventoryService.class);
        PaymentGateway paymentGateway = Mockito.mock(PaymentGateway.class);

        Mockito.when(showRepo.findById(anyLong())).thenReturn(Optional.empty());

        BookingService service = new BookingService(showRepo, bookingRepo, bookingSeatRepo, inventoryService, paymentGateway);

        BookingRequest req = new BookingRequest();
        req.setShowId(999L);
        req.setSeatIds(of(1L));
        req.setCustomerName("A");
        req.setCustomerEmail("a@test.com");

        assertThrows(RuntimeException.class, () -> service.createBooking(req));
    }

    @Test
    public void createBooking_success_shouldConfirm() {
        ShowRepository showRepo = Mockito.mock(ShowRepository.class);
        BookingRepository bookingRepo = Mockito.mock(BookingRepository.class);
        BookingSeatRepository bookingSeatRepo = Mockito.mock(BookingSeatRepository.class);
        InventoryService inventoryService = Mockito.mock(InventoryService.class);
        PaymentGateway paymentGateway = Mockito.mock(PaymentGateway.class);

        Show show = new Show();
        show.setId(101L);
        show.setTheatreId(1L);
        show.setScreenId(10L);
        show.setMovieTitle("X");
        show.setShowTime(LocalDateTime.now().plusHours(2));
        Mockito.when(showRepo.findById(101L)).thenReturn(Optional.of(show));

        com.xyz.moviebooking.entity.ShowSeat ss = new com.xyz.moviebooking.entity.ShowSeat();
        ss.setId(1L);
        ss.setShowId(101L);
        ss.setSeatId(501L);
        ss.setStatus(com.xyz.moviebooking.entity.SeatStatus.RESERVED);

        Mockito.when(inventoryService.reserveSeats(eq(101L), anyList())).thenReturn(of(ss));
        Mockito.doNothing().when(inventoryService).markBooked(anyList());

        Mockito.when(paymentGateway.charge(anyString(), anyDouble())).thenReturn(true);

        Mockito.when(bookingRepo.save(any())).thenAnswer(inv -> {
            com.xyz.moviebooking.entity.Booking b = inv.getArgument(0);
            if (b.getId() == null) b.setId("test-booking-1");
            if (b.getCreatedAt() == null) b.setCreatedAt(java.time.LocalDateTime.now());
            return b;
        });

        BookingService service = new BookingService(showRepo, bookingRepo, bookingSeatRepo, inventoryService, paymentGateway);

        BookingRequest req = new BookingRequest();
        req.setShowId(101L);
        req.setSeatIds(of(501L));
        req.setCustomerName("Sebu");
        req.setCustomerEmail("sebu@test.com");

        BookingResponse resp = service.createBooking(req);

        assertNotNull(resp.getBookingId());
        assertEquals("CONFIRMED", resp.getStatus());
    }

}
