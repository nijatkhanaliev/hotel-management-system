package com.company.hotelmanagementsystem.mapper;

import com.company.hotelmanagementsystem.dto.request.RoomRequest;
import com.company.hotelmanagementsystem.dto.response.RoomResponse;
import com.company.hotelmanagementsystem.entity.Hotel;
import com.company.hotelmanagementsystem.entity.Room;
import com.company.hotelmanagementsystem.exception.NotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

import static com.company.hotelmanagementsystem.exception.constant.ErrorCode.NOT_FOUND;
import static com.company.hotelmanagementsystem.exception.constant.ErrorMessage.NOT_FOUND_MESSAGE;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "roomStatus", constant = "AVAILABLE")
    Room toRoom(RoomRequest roomRequest);

    @Mapping(target = "hotelId", source = "room", qualifiedByName = "getHotelId")
    RoomResponse toRoomResponse(Room room);

    List<RoomResponse> toRoomResponses(List<Room> rooms);

    @Named("getHotelId")
    default Long getHotelId(Room room) {
        return room.getHotel().getId();
    }

}
