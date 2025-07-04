package com.company.hotelmanagementsystem.mapper;

import com.company.hotelmanagementsystem.dto.request.BookingRequest;
import com.company.hotelmanagementsystem.dto.response.BookingResponse;
import com.company.hotelmanagementsystem.entity.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = RoomMapper.class)
public interface BookingMapper {

    Booking toBooking(BookingRequest bookingRequest);

    BookingResponse toBookingResponse(Booking booking);

}
