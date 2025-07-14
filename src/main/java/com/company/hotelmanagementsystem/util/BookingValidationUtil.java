package com.company.hotelmanagementsystem.util;

import com.company.hotelmanagementsystem.dto.request.BookingRequest;
import com.company.hotelmanagementsystem.entity.Room;
import com.company.hotelmanagementsystem.enums.RoomStatus;
import com.company.hotelmanagementsystem.exception.BookingValidationException;

import java.time.LocalDate;

import static com.company.hotelmanagementsystem.exception.constant.ErrorCode.BOOKING_IS_NOT_VALID;
import static com.company.hotelmanagementsystem.exception.constant.ErrorMessage.BOOKING_IS_NOT_VALID_MESSAGE;

public class BookingValidationUtil {

    public static void isDateValid(BookingRequest bookingRequest){
        if ((bookingRequest.getStartDate().isAfter(bookingRequest.getEndDate())) ||
                bookingRequest.getStartDate().isBefore(LocalDate.now()) ||
                bookingRequest.getStartDate().equals(bookingRequest.getEndDate())) {
            throw new BookingValidationException(BOOKING_IS_NOT_VALID_MESSAGE,BOOKING_IS_NOT_VALID);
        }

    }

    public static void isRoomAvailable(Room room,BookingRequest bookingRequest){
        if (room.getRoomStatus() == RoomStatus.BOOKED &&
                room.getBooking().getEndDate().isAfter(bookingRequest.getStartDate())) {
            throw new BookingValidationException(BOOKING_IS_NOT_VALID_MESSAGE,BOOKING_IS_NOT_VALID);
        }
    }

}
