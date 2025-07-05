package com.company.hotelmanagementsystem.service;

import com.company.hotelmanagementsystem.dto.request.BookingRequest;
import com.company.hotelmanagementsystem.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest bookingRequest);

    List<BookingResponse> getAllBooking();

    BookingResponse getBookingById(long id);

    void cancelBooking(long id);
}
