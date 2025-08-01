package com.company.hotelmanagementsystem.mapper;

import com.company.hotelmanagementsystem.dto.request.HotelRequest;
import com.company.hotelmanagementsystem.dto.response.HotelDescriptionResponse;
import com.company.hotelmanagementsystem.dto.response.HotelProjection;
import com.company.hotelmanagementsystem.dto.response.HotelResponse;
import com.company.hotelmanagementsystem.entity.Hotel;
import com.company.hotelmanagementsystem.entity.HotelDescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = HotelDescriptionMapper.class)
public interface HotelMapper {

    @Mapping(target = "descriptions", source = "hotelDescriptionRequests")
    Hotel toHotel(HotelRequest hotelRequest);

    @Mapping(target = "description", source = "hotel", qualifiedByName = "getHotelDescription")
    HotelResponse toHotelResponse(Hotel hotel);

    @Mapping(target = "description", source = "description", qualifiedByName = "mapDescription")
    HotelResponse toHotelResponseFromProject(HotelProjection projection);

    @Named("getHotelDescription")
    default HotelDescription getHotelDescription(Hotel hotel){
        List<HotelDescription> descriptions = hotel.getDescriptions();

        return descriptions.getFirst();
    }

    @Named("mapDescription")
    default HotelDescriptionResponse mapDescription(String des){
        if(des==null){
            return null;
        }

        return HotelDescriptionResponse
                .builder()
                .description(des)
                .build();
    }

}
