package com.company.hotelmanagementsystem.mapper;

import com.company.hotelmanagementsystem.dto.request.RoomRequest;
import com.company.hotelmanagementsystem.dto.response.RoomResponse;
import com.company.hotelmanagementsystem.entity.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {

   Room toRoom(RoomRequest roomRequest);

   RoomResponse toRoomResponse(Room room);

}
