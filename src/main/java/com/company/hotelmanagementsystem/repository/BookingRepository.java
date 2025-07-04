package com.company.hotelmanagementsystem.repository;

import com.company.hotelmanagementsystem.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
