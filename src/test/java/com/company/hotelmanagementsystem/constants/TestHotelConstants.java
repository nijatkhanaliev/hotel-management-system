package com.company.hotelmanagementsystem.constants;

import com.company.hotelmanagementsystem.dto.request.HotelDescriptionRequest;
import com.company.hotelmanagementsystem.dto.request.HotelRequest;
import com.company.hotelmanagementsystem.dto.response.HotelDescriptionResponse;
import com.company.hotelmanagementsystem.dto.response.HotelProjection;
import com.company.hotelmanagementsystem.dto.response.HotelResponse;


import java.time.LocalDateTime;
import java.util.List;

public interface TestHotelConstants {

    Long HOTEL_ID = 123L;
    String USER_LANG_ENG = "eng";

    HotelProjection HOTEL_PROJECTION = new HotelProjection() {
        @Override
        public Long getId() {
            return 1L;
        }

        @Override
        public String getName() {
            return "Test";
        }

        @Override
        public String getLocation() {
            return "TEST";
        }

        @Override
        public LocalDateTime getCreatedAt() {
            return LocalDateTime.now();
        }

        @Override
        public String getDescription() {
            return "TEST TEST TEST";
        }
    };

    HotelDescriptionRequest HOTEL_DESCRIPTION_REQUEST_ENG = HotelDescriptionRequest.builder()
            .language("eng")
            .description("Test ENG")
            .build();

    HotelDescriptionRequest HOTEL_DESCRIPTION_REQUEST_TR = HotelDescriptionRequest.builder()
            .language("tr")
            .description("Test TR")
            .build();

    HotelDescriptionResponse HOTEL_DESCRIPTION_RESPONSE_ENG = HotelDescriptionResponse
            .builder()
            .description("Test ENG")
            .build();

    HotelRequest HOTEL_REQUEST = HotelRequest
            .builder()
            .name("Test Hotel")
            .location("Test")
            .hotelDescriptionRequests(
                    List.of(HOTEL_DESCRIPTION_REQUEST_TR, HOTEL_DESCRIPTION_REQUEST_ENG)
            )
            .build();

    HotelRequest HOTEL_REQUEST_DUPLICATE_DESC = HotelRequest
            .builder()
            .name("Test Hotel")
            .location("Test")
            .hotelDescriptionRequests(
                    List.of(HOTEL_DESCRIPTION_REQUEST_TR, HOTEL_DESCRIPTION_REQUEST_TR)
            )
            .build();

    HotelResponse HOTEL_RESPONSE_ENG = HotelResponse
            .builder()
            .name("Test Hotel")
            .location("Test")
            .createdAt(LocalDateTime.now())
            .id(1L)
            .description(HOTEL_DESCRIPTION_RESPONSE_ENG)
            .build();

}
