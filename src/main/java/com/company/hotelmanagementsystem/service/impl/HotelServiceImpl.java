package com.company.hotelmanagementsystem.service.impl;

import com.company.hotelmanagementsystem.dto.request.HotelRequest;
import com.company.hotelmanagementsystem.dto.response.HotelResponse;
import com.company.hotelmanagementsystem.entity.Hotel;
import com.company.hotelmanagementsystem.exception.NotFoundException;
import com.company.hotelmanagementsystem.mapper.HotelMapper;
import com.company.hotelmanagementsystem.repository.HotelRepository;
import com.company.hotelmanagementsystem.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Override
    public HotelResponse createHotel(HotelRequest hotelRequest) {
        Hotel hotel = hotelMapper.toHotel(hotelRequest);

        hotelRepository.save(hotel);

        return hotelMapper.toHotelResponse(hotel);
    }

    @Override
    public List<HotelResponse> getAllHotel() {
        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream()
                .map(hotelMapper::toHotelResponse)
                .toList();
    }

    @Override
    public HotelResponse getHotelById(long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Hotel not found with ID: " + id));

        return hotelMapper.toHotelResponse(hotel);
    }

    @Override
    public HotelResponse updateHotel(long id, HotelRequest hotelRequest) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Hotel not found with ID: " + id));

        hotel.setName(hotelRequest.getName());
        hotel.setLocation(hotelRequest.getLocation());

        hotelRepository.save(hotel);

        return hotelMapper.toHotelResponse(hotel);
    }

    @Override
    public void deleteHotel(long id) {
       Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Hotel not found with ID: " + id));

        hotelRepository.delete(hotel);
    }

}
