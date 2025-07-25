package com.company.hotelmanagementsystem.dto.response;

import java.time.LocalDateTime;

public interface HotelProjection {
        Long getId();
        String getName();
        String getLocation();
        LocalDateTime getCreatedAt();
        String getDescription();
}
