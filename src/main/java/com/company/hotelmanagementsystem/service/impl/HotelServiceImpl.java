package com.company.hotelmanagementsystem.service.impl;

import com.company.hotelmanagementsystem.dto.request.HotelRequest;
import com.company.hotelmanagementsystem.dto.response.HotelResponse;
import com.company.hotelmanagementsystem.entity.Hotel;
import com.company.hotelmanagementsystem.exception.NotFoundException;
import com.company.hotelmanagementsystem.mapper.HotelMapper;
import com.company.hotelmanagementsystem.repository.HotelRepository;
import com.company.hotelmanagementsystem.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Override
    public HotelResponse createHotel(HotelRequest hotelRequest) {
        log.info("Creating new hotel");
        Hotel hotel = hotelMapper.toHotel(hotelRequest);

        hotelRepository.save(hotel);

        return hotelMapper.toHotelResponse(hotel);
    }

    @Override
    public List<HotelResponse> getAllHotel() {
        log.info("Getting all hotels");
        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream()
                .map(hotelMapper::toHotelResponse)
                .toList();
    }

    @Override
    public HotelResponse getHotelById(long id) {
        log.info("Getting hotel by id '{}'",id);
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Hotel not found with ID: " + id));

        return hotelMapper.toHotelResponse(hotel);
    }

    @Override
    public HotelResponse updateHotel(long id, HotelRequest hotelRequest) {
        log.info("Getting Hotel by ID '{}'",id);
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Hotel not found with ID: " + id));

        log.info("Updating Hotel, hotelID '{}'",id);
        hotel.setName(hotelRequest.getName());
        hotel.setLocation(hotelRequest.getLocation());
        hotelRepository.save(hotel);

        return hotelMapper.toHotelResponse(hotel);
    }

    @Override
    public void deleteHotel(long id) {
        log.info("Getting hotel by ID '{}'",id);
       Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Hotel not found with ID: " + id));

        log.warn("Deleting hotel, hotelId '{}'",id);
        hotelRepository.delete(hotel);
    }

}
