package com.company.hotelmanagementsystem.service.impl;

import com.company.hotelmanagementsystem.dto.request.HotelRequest;
import com.company.hotelmanagementsystem.dto.response.HotelProjection;
import com.company.hotelmanagementsystem.dto.response.HotelResponse;
import com.company.hotelmanagementsystem.entity.Hotel;
import com.company.hotelmanagementsystem.exception.NotFoundException;
import com.company.hotelmanagementsystem.mapper.HotelDescriptionMapper;
import com.company.hotelmanagementsystem.mapper.HotelMapper;
import com.company.hotelmanagementsystem.repository.HotelRepository;
import com.company.hotelmanagementsystem.service.HotelService;
import com.company.hotelmanagementsystem.util.HotelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.company.hotelmanagementsystem.exception.constant.ErrorCode.NOT_FOUND;
import static com.company.hotelmanagementsystem.exception.constant.ErrorMessage.NOT_FOUND_MESSAGE;


@Slf4j
@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final HotelDescriptionMapper hotelDescriptionMapper;

    @Override
    public HotelResponse createHotel(HotelRequest hotelRequest, String lang) {
        log.info("Creating new hotel");

        HotelUtil.validateUniqueLanguage(hotelRequest);
        Hotel hotel = hotelMapper.toHotel(hotelRequest);
        hotel.getDescriptions().forEach((d) -> d.setHotel(hotel));
        hotelRepository.save(hotel);
        HotelResponse response = hotelMapper.toHotelResponse(hotel);

        hotel.getDescriptions()
                .stream()
                .filter((d) -> d.getLanguage().equalsIgnoreCase(lang))
                .findFirst()
                .map(hotelDescriptionMapper::toHotelDescriptionResponse)
                .ifPresentOrElse(response::setDescription, () -> {
                    hotel.getDescriptions().stream()
                            .filter((d) -> d.getLanguage().equalsIgnoreCase("eng"))
                            .findFirst()
                            .map(hotelDescriptionMapper::toHotelDescriptionResponse)
                            .ifPresent(response::setDescription);
                });

        return response;
    }

    @Override
    public List<HotelResponse> getAllHotel(String lang) {
        log.info("Getting all hotels, lang: {}", lang);

        List<HotelProjection> hotelProjections = hotelRepository.findAllWithDescriptions(lang);

        return hotelProjections.stream()
                .map(hotelMapper::toHotelResponseFromProject)
                .toList();
    }

    @Override
    public HotelResponse getHotelById(Long id, String lang) {
        log.info("Getting hotel by id '{}'", id);
        HotelProjection projection = hotelRepository.findHotelWithDescription(id, lang)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE, NOT_FOUND));

        return hotelMapper.toHotelResponseFromProject(projection);
    }

    @Override
    public HotelResponse updateHotel(Long id, HotelRequest hotelRequest) {
        log.info("Getting Hotel by ID '{}'", id);
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE, NOT_FOUND));

        log.info("Updating Hotel, hotelID '{}'", id);
        hotel.setName(hotelRequest.getName());
        hotel.setLocation(hotelRequest.getLocation());
        hotelRepository.save(hotel);

        return hotelMapper.toHotelResponse(hotel);
    }

    @Override
    public void deleteHotel(Long id) {
        log.info("Getting hotel by ID '{}'", id);
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE, NOT_FOUND));

        log.warn("Deleting hotel, hotelId '{}'", id);
        hotelRepository.delete(hotel);
    }

}
