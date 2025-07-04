package com.company.hotelmanagementsystem.service.impl;

import com.company.hotelmanagementsystem.dto.request.RoomRequest;
import com.company.hotelmanagementsystem.dto.response.RoomResponse;
import com.company.hotelmanagementsystem.entity.Hotel;
import com.company.hotelmanagementsystem.entity.Room;
import com.company.hotelmanagementsystem.enums.RoomStatus;
import com.company.hotelmanagementsystem.exception.NotFoundException;
import com.company.hotelmanagementsystem.mapper.RoomMapper;
import com.company.hotelmanagementsystem.repository.HotelRepository;
import com.company.hotelmanagementsystem.repository.RoomRepository;
import com.company.hotelmanagementsystem.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelRepository hotelRepository;

    @Override
    public RoomResponse createRoom(RoomRequest roomRequest) {
        Room room = roomMapper.toRoom(roomRequest);
        room.setRoomStatus(RoomStatus.AVAILABLE);

        roomRepository.save(room);

        return roomMapper.toRoomResponse(room);
    }

    @Override
    public List<RoomResponse> getAllRoomByHotel(long id) {
        List<Room> rooms = roomRepository.findAllByHotelId(id);

        return rooms.stream()
                .map(roomMapper::toRoomResponse)
                .toList();
    }

    @Override
    public RoomResponse getRoomById(long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found by ID: " + id));

        return roomMapper.toRoomResponse(room);
    }

    @Override
    public RoomResponse updateRoom(long id, RoomRequest roomRequest) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found by ID: " + id));

        Hotel hotel = hotelRepository.findById(roomRequest.getHotelId())
                        .orElseThrow(()-> new NotFoundException("Hotel not found with ID: " + id));


        room.setRoomNumber(roomRequest.getRoomNumber());
        room.setPrice(roomRequest.getPrice());
        room.setHotel(hotel);

        roomRepository.save(room);

        return roomMapper.toRoomResponse(room);
    }

    @Override
    public void deleteRoom(long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found by ID: " + id));

        roomRepository.delete(room);
    }
}
