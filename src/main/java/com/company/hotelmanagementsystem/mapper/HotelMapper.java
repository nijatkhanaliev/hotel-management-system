package com.company.hotelmanagementsystem.mapper;

import com.company.hotelmanagementsystem.dto.request.HotelRequest;
import com.company.hotelmanagementsystem.dto.response.HotelResponse;
import com.company.hotelmanagementsystem.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = HotelDescriptionMapper.class)
public interface HotelMapper {

    @Mapping(target = "descriptions", source = "hotelDescriptionRequests")
    Hotel toHotel(HotelRequest hotelRequest);

    @Mapping(target = "description", source = "descriptions")
    HotelResponse toHotelResponse(Hotel hotel);

}
