package com.company.hotelmanagementsystem.service;


import com.company.hotelmanagementsystem.dto.request.RoomRequest;
import com.company.hotelmanagementsystem.dto.response.RoomResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoomService {
    RoomResponse createRoom(RoomRequest roomRequest);

    List<RoomResponse> getAllRoomByHotel(long id);

    RoomResponse getRoomById(long id);

    RoomResponse updateRoom(long id, RoomRequest roomRequest);

    void deleteRoom(long id);

    List<RoomResponse> createRoomsFromExcel(MultipartFile excelFile);
}
