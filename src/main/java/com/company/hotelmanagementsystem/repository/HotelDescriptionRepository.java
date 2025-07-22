package com.company.hotelmanagementsystem.repository;

import com.company.hotelmanagementsystem.entity.HotelDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelDescriptionRepository extends JpaRepository<HotelDescription, Long> {
}