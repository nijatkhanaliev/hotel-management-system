package com.company.hotelmanagementsystem.repository;

import com.company.hotelmanagementsystem.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByHotelId(long id);
}
