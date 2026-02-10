package com.xyz.moviebooking.service;

import com.xyz.moviebooking.entity.SeatStatus;
import com.xyz.moviebooking.entity.ShowSeat;
import com.xyz.moviebooking.exception.SeatUnavailableException;
import com.xyz.moviebooking.repository.ShowSeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final ShowSeatRepository showSeatRepository;

    public InventoryService(ShowSeatRepository showSeatRepository) {
        this.showSeatRepository = showSeatRepository;
    }

    @Transactional
    public List<ShowSeat> reserveSeats(Long showId, List<Long> seatIds) {
        List<ShowSeat> showSeats = showSeatRepository.findByShowIdAndSeatIdIn(showId, seatIds);

        if (showSeats.size() != seatIds.size()) {
            throw new SeatUnavailableException("One or more seats not found for the show.");
        }

        for (ShowSeat ss : showSeats) {
            if (ss.getStatus() != SeatStatus.AVAILABLE) {
                throw new SeatUnavailableException("Seat already reserved/booked: seatId=" + ss.getSeatId());
            }
        }

        for (ShowSeat ss : showSeats) {
            ss.setStatus(SeatStatus.RESERVED);
        }

        return showSeatRepository.saveAll(showSeats);
    }

    @Transactional
    public void markBooked(List<ShowSeat> reserved) {
        for (ShowSeat ss : reserved) ss.setStatus(SeatStatus.BOOKED);
        showSeatRepository.saveAll(reserved);
    }

    @Transactional
    public void release(List<ShowSeat> reserved) {
        for (ShowSeat ss : reserved) ss.setStatus(SeatStatus.AVAILABLE);
        showSeatRepository.saveAll(reserved);
    }
}
