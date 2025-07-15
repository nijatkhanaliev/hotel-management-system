package com.company.hotelmanagementsystem.controller;

import com.company.hotelmanagementsystem.dto.request.BookingRequest;
import com.company.hotelmanagementsystem.dto.response.BookingResponse;
import com.company.hotelmanagementsystem.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest bookingRequest) {
        return ResponseEntity.status(CREATED)
                .body(bookingService.createBooking(bookingRequest));
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBooking() {
        return ResponseEntity.ok(bookingService.getAllBooking());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable long id) {
        bookingService.cancelBooking(id);

        return ResponseEntity.status(NO_CONTENT).build();
    }
}
