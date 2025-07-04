package com.company.hotelmanagementsystem.mapper;

import com.company.hotelmanagementsystem.dto.request.HotelRequest;
import com.company.hotelmanagementsystem.dto.response.HotelResponse;
import com.company.hotelmanagementsystem.entity.Hotel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    Hotel toHotel(HotelRequest hotelRequest);

    HotelResponse toHotelResponse(Hotel hotel);

}
