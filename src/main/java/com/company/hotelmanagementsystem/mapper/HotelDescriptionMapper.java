package com.company.hotelmanagementsystem.mapper;

import com.company.hotelmanagementsystem.dto.request.HotelDescriptionRequest;
import com.company.hotelmanagementsystem.dto.response.HotelDescriptionResponse;
import com.company.hotelmanagementsystem.entity.HotelDescription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelDescriptionMapper {

    HotelDescription toHotelDescription(HotelDescriptionRequest request);

    HotelDescriptionResponse toHotelDescriptionResponse(HotelDescriptionResponse request);
}
