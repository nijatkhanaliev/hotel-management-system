package com.company.hotelmanagementsystem.mapper;

import com.company.hotelmanagementsystem.dto.request.HotelDescriptionRequest;
import com.company.hotelmanagementsystem.dto.response.HotelDescriptionResponse;
import com.company.hotelmanagementsystem.entity.HotelDescription;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelDescriptionMapper {

    HotelDescription toHotelDescription(HotelDescriptionRequest request);

    HotelDescriptionResponse toHotelDescriptionResponse(HotelDescription hotel);

    List<HotelDescription> toHotelDescriptions(List<HotelDescriptionRequest> requests);
}
