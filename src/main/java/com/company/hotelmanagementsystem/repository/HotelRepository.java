package com.company.hotelmanagementsystem.repository;

import com.company.hotelmanagementsystem.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
}
