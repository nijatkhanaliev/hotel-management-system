package com.company.hotelmanagementsystem.dto.response;

import com.company.hotelmanagementsystem.enums.RoomStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RoomResponse {
    private long id;
    private Long hotelId;
    private String roomNumber;
    private double price;
    private RoomStatus roomStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;
}
