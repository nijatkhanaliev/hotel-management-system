package com.company.hotelmanagementsystem.repository;

import com.company.hotelmanagementsystem.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("select distinct h from Hotel h join fetch h.descriptions d where lower(d.language) in (lower(:lang), 'eng')")
    List<Hotel> findAllWithDescriptions(@Param("lang") String language);

    @Query("select h from Hotel h join fetch h.descriptions d where h.id = :id and lower(d.language) in (lower(:lang), 'eng')")
    Optional<Hotel> findHotelWithDescription(@Param("id") Long id, @Param("lang") String language);

}
