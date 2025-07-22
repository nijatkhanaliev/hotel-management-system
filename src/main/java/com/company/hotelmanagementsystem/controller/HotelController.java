package com.company.hotelmanagementsystem.controller;

import com.company.hotelmanagementsystem.dto.request.HotelRequest;
import com.company.hotelmanagementsystem.dto.response.HotelResponse;
import com.company.hotelmanagementsystem.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/hotels")
public class HotelController {
    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelResponse> createHotel(@Valid @RequestBody HotelRequest hotelRequest,
                                                     @RequestHeader("lang") String lang) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hotelService.createHotel(hotelRequest, lang));
    }

    @GetMapping
    public ResponseEntity<List<HotelResponse>> getAllHotel(@RequestHeader(name = "lang") String lang) {
        return ResponseEntity.ok(hotelService.getAllHotel(lang));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getHotelById(@PathVariable long id,
                                                      @RequestHeader(name = "lang") String lang) {
        return ResponseEntity.ok(hotelService.getHotelById(id,lang));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> updateHotel(@PathVariable long id,
                                                     @Valid @RequestBody HotelRequest hotelRequest) {
        return ResponseEntity.ok(hotelService.updateHotel(id, hotelRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable long id) {
        hotelService.deleteHotel(id);

        return ResponseEntity.status(NO_CONTENT).build();
    }

}
