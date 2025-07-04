package com.company.hotelmanagementsystem.dto.response;

import com.company.hotelmanagementsystem.enums.RoomStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class RoomResponse {
    private long id;
    private Long hotelId;
    private String roomNumber;
    private double price;
    private RoomStatus roomStatus;
    private LocalDateTime createdAt;
}
