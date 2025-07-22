package com.company.hotelmanagementsystem.service;

import com.company.hotelmanagementsystem.dto.request.HotelRequest;
import com.company.hotelmanagementsystem.dto.response.HotelResponse;

import java.util.List;

public interface HotelService {
    HotelResponse createHotel(HotelRequest hotelRequest);

    List<HotelResponse> getAllHotel();

    HotelResponse getHotelById(Long id);

    HotelResponse updateHotel(Long id, HotelRequest hotelRequest);

    void deleteHotel(Long id);
}
