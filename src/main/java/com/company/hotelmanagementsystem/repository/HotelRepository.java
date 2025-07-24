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
            select distinct on (h.id) h.id as id, h.name as name, h.location as location, h.created_at
            as createdAt,d.description as description from hotels h 
            join  hotel_description d on d.hotel_id=h.id
            where lower(d.language) in (lower(:lang), 'eng')
            order by h.id, case
            when lower(d.language)=lower(:lang) then 0
            when lower(d.language)='eng' then 1
            end
            """, nativeQuery = true)
    List<HotelProjection> findAllWithDescriptions(@Param("lang") String language);

    @Query("select h from Hotel h join fetch h.descriptions d where h.id = :id and lower(d.language) in (lower(:lang), 'eng')")
    Optional<Hotel> findHotelWithDescription(@Param("id") Long id, @Param("lang") String language);

}
