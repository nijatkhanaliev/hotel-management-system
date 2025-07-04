package com.company.hotelmanagementsystem.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
public class HotelResponse {
    private long id;
    private String name;
    private String location;
    private LocalDateTime createdAt;
}
