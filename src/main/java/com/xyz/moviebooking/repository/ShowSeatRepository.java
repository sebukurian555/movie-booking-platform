package com.xyz.moviebooking.repository;

import com.xyz.moviebooking.entity.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    List<ShowSeat> findByShowIdAndSeatIdIn(Long showId, List<Long> seatIds);
    Optional<ShowSeat> findByShowIdAndSeatId(Long showId, Long seatId);
}
