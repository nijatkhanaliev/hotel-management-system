package com.company.hotelmanagementsystem.dto.response;

import com.company.hotelmanagementsystem.enums.BookingStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
public class BookingResponse {
    private long id;
    private RoomResponse room;
    private String customerName;
    private String customerEmail;
    private LocalDate startDate;
    private LocalDate endDate;
    private BookingStatus bookingStatus;
}
