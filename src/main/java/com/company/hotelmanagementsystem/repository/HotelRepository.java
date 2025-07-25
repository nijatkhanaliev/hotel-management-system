package com.company.hotelmanagementsystem.repository;

import com.company.hotelmanagementsystem.dto.response.HotelProjection;
import com.company.hotelmanagementsystem.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query(value = """
            select distinct on (h.id) h.id, h.name, h.location, h.created_at ,d.description 
            from hotels h join  hotel_description d on d.hotel_id=h.id
            where lower(d.language) in (lower(:lang), 'eng')
            order by h.id, case
            when lower(d.language)=lower(:lang) then 0
            when lower(d.language)='eng' then 1
            end
            """, nativeQuery = true)
    List<HotelProjection> findAllWithDescriptions(@Param("lang") String language);

    @Query(value = """
            select distinct on(h.id) h.id, h.name, h.location, h.created_at, d.description  
            from hotels h join hotel_description d on d.hotel_id=h.id 
            where h.id = :id and lower(d.language) in (lower(:lang), 'eng')
            order by h.id, case
            when lower(d.language) = lower(:lang) then 0
            when lower(d.language) = lower('eng') then 1
            end
            """, nativeQuery = true)
    Optional<HotelProjection> findHotelWithDescription(@Param("id") Long id, @Param("lang") String language);

}
