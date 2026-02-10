package com.xyz.moviebooking.controller;

import com.xyz.moviebooking.dto.BookingRequest;
import com.xyz.moviebooking.dto.BookingResponse;
import com.xyz.moviebooking.service.BookingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingControllerTest {

    @Test
    public void create_shouldReturnCreated() {
        BookingService service = Mockito.mock(BookingService.class);
        BookingController controller = new BookingController(service);

        BookingRequest req = new BookingRequest();
        req.setShowId(101L);
        req.setSeatIds(List.of(501L, 502L));
        req.setCustomerName("Sebu");
        req.setCustomerEmail("sebu@test.com");

        Mockito.when(service.createBooking(Mockito.any()))
                .thenReturn(new BookingResponse("id1", "CONFIRMED", 101L, List.of("A1","A2"), 500.0));

        BookingResponse resp = controller.create(req);
        assertEquals("CONFIRMED", resp.getStatus());
        assertEquals(101L, resp.getShowId());
    }
}
