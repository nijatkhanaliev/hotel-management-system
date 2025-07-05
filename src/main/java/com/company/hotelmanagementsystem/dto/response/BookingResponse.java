package com.company.hotelmanagementsystem.dto.response;

import com.company.hotelmanagementsystem.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingResponse {
    private long id;
    private RoomResponse room;
    private String customerName;
    private String customerEmail;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate endDate;
    private BookingStatus bookingStatus;
}
