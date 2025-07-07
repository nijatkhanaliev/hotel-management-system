package com.company.hotelmanagementsystem.service.impl;

import com.company.hotelmanagementsystem.dto.request.BookingRequest;
import com.company.hotelmanagementsystem.dto.response.BookingResponse;
import com.company.hotelmanagementsystem.entity.Booking;
import com.company.hotelmanagementsystem.entity.Room;
import com.company.hotelmanagementsystem.enums.BookingStatus;
import com.company.hotelmanagementsystem.enums.RoomStatus;
import com.company.hotelmanagementsystem.exception.NotFoundException;
import com.company.hotelmanagementsystem.mapper.BookingMapper;
import com.company.hotelmanagementsystem.repository.BookingRepository;
import com.company.hotelmanagementsystem.repository.RoomRepository;
import com.company.hotelmanagementsystem.service.BookingService;
import com.company.hotelmanagementsystem.util.BookingValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        long roomId = bookingRequest.getRoomId();
        log.info("Getting room by id '{}'",roomId);
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("Room not found with ID: " + roomId));

        log.info("Creating new booking with roomID '{}'",roomId);
        Booking booking = bookingMapper.toBooking(bookingRequest);
        booking.setRoom(room);
        booking.setBookingStatus(BookingStatus.ACTIVE);
        bookingRepository.save(booking);

        BookingValidationUtil.isDateValid(bookingRequest);
        BookingValidationUtil.isRoomAvailable(room, bookingRequest);

        log.info("Updating room booking, roomId '{}'",roomId);
        room.setBooking(booking);
        room.setRoomStatus(RoomStatus.BOOKED);
        roomRepository.save(room);

        return bookingMapper.toBookingResponse(booking);
    }

    @Override
    public List<BookingResponse> getAllBooking() {
        log.info("Getting all booking");
        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream()
                .map(bookingMapper::toBookingResponse)
                .toList();
    }

    @Override
    public BookingResponse getBookingById(long id) {
        log.info("Getting booking by ID '{}'",id);
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found with ID:" + id));

        return bookingMapper.toBookingResponse(booking);
    }

    @Override
    @Transactional
    public void cancelBooking(long id) {
        log.info("Getting booking by id '{}'",id);
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found with ID:" + id));

        log.warn("Cancelling booking by ID '{}'",id);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        long roomId = booking.getRoom().getId();

        log.info("Getting room by ID '{}'",roomId);
        Room room = roomRepository.findById(booking.getRoom().getId())
                .orElseThrow(() -> new NotFoundException("Room not found with ID: " + roomId));
        log.info("Updating room, roomId '{}'",roomId);
        room.setBooking(null);
        room.setRoomStatus(RoomStatus.AVAILABLE);
        roomRepository.save(room);
    }
}
