package com.xyz.moviebooking.service;

import com.xyz.moviebooking.dto.BookingRequest;
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
        req.setSeatIds(List.of(1L));
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

        Mockito.when(inventoryService.reserveSeats(eq(101L), anyList())).thenReturn(List.of());
        Mockito.when(paymentGateway.charge(anyString(), anyDouble())).thenReturn(true);

        Mockito.when(bookingRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        BookingService service = new BookingService(showRepo, bookingRepo, bookingSeatRepo, inventoryService, paymentGateway);

        BookingRequest req = new BookingRequest();
        req.setShowId(101L);
        req.setSeatIds(List.of(501L));
        req.setCustomerName("Sebu");
        req.setCustomerEmail("sebu@test.com");

        var resp = service.createBooking(req);
        assertNotNull(resp.getBookingId());
        assertEquals("CONFIRMED", resp.getStatus());
    }
}
