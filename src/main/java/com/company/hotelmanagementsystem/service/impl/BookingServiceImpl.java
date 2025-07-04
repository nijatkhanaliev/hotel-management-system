package com.company.hotelmanagementsystem.service.impl;

import com.company.hotelmanagementsystem.dto.request.BookingRequest;
import com.company.hotelmanagementsystem.dto.response.BookingResponse;
import com.company.hotelmanagementsystem.entity.Booking;
import com.company.hotelmanagementsystem.entity.Room;
import com.company.hotelmanagementsystem.enums.BookingStatus;
import com.company.hotelmanagementsystem.enums.RoomStatus;
import com.company.hotelmanagementsystem.exception.InvalidException;
import com.company.hotelmanagementsystem.exception.NotFoundException;
import com.company.hotelmanagementsystem.mapper.BookingMapper;
import com.company.hotelmanagementsystem.repository.BookingRepository;
import com.company.hotelmanagementsystem.repository.RoomRepository;
import com.company.hotelmanagementsystem.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest bookingRequest) {

        if ((bookingRequest.getStartDate().isAfter(bookingRequest.getEndDate())) || bookingRequest.getStartDate().isBefore(LocalDate.now()) || bookingRequest.getStartDate().equals(bookingRequest.getEndDate())) {
            throw new InvalidException("Start date or End date is not valid");
        }

        Room room = roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(() -> new NotFoundException("Room not found with ID: " + bookingRequest.getRoomId()));

        if (room.getRoomStatus() == RoomStatus.BOOKED && room.getBooking().getEndDate().isAfter(bookingRequest.getStartDate())) {
            throw new InvalidException("Room is not available");
        }

        Booking booking = bookingMapper.toBooking(bookingRequest);
        booking.setRoom(room);
        booking.setBookingStatus(BookingStatus.ACTIVE);

        bookingRepository.save(booking);

        room.setBooking(booking);
        room.setRoomStatus(RoomStatus.BOOKED);

        roomRepository.save(room);

        return bookingMapper.toBookingResponse(booking);
    }

    @Override
    public List<BookingResponse> getAllBooking() {
        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream()
                .map(bookingMapper::toBookingResponse)
                .toList();
    }

    @Override
    public BookingResponse getBookingById(long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found with ID:" + id));

        return bookingMapper.toBookingResponse(booking);
    }

    @Override
    @Transactional
    public void deleteBooking(long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found with ID:" + id));

        Room room = roomRepository.findById(booking.getRoom().getId())
                .orElseThrow(() -> new NotFoundException("Room not found with ID: " + booking.getRoom().getId()));

        room.setBooking(null);
        room.setRoomStatus(RoomStatus.AVAILABLE);

        roomRepository.save(room);

        bookingRepository.delete(booking);
    }
}
