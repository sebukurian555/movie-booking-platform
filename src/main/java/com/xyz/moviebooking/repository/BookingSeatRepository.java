package com.xyz.moviebooking.repository;

import com.xyz.moviebooking.entity.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {}
