package com.company.hotelmanagementsystem.service;

import com.company.hotelmanagementsystem.dto.request.HotelRequest;
import com.company.hotelmanagementsystem.dto.response.HotelResponse;

import java.util.List;

public interface HotelService {
    HotelResponse createHotel(HotelRequest hotelRequest, String lang);

    List<HotelResponse> getAllHotel(String lang);

    HotelResponse getHotelById(Long id, String lang);

    HotelResponse updateHotel(Long id, HotelRequest hotelRequest);

    void deleteHotel(Long id);
}
