package com.company.hotelmanagementsystem.util;

import com.company.hotelmanagementsystem.dto.request.BookingRequest;
import com.company.hotelmanagementsystem.entity.Room;
import com.company.hotelmanagementsystem.enums.RoomStatus;
import com.company.hotelmanagementsystem.exception.InvalidException;

import java.time.LocalDate;

public class ValidateBooking {

    public static void isDateValid(BookingRequest bookingRequest){
        if ((bookingRequest.getStartDate().isAfter(bookingRequest.getEndDate())) || bookingRequest.getStartDate().isBefore(LocalDate.now()) || bookingRequest.getStartDate().equals(bookingRequest.getEndDate())) {
            throw new InvalidException("Start date or End date is not valid");
        }

    }

    public static void isRoomAvailable(Room room,BookingRequest bookingRequest){
        if (room.getRoomStatus() == RoomStatus.BOOKED && room.getBooking().getEndDate().isAfter(bookingRequest.getStartDate())) {
            throw new InvalidException("Room is not available");
        }
    }

}
