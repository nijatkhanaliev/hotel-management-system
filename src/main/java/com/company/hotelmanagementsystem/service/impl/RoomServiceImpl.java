package com.company.hotelmanagementsystem.service.impl;

import com.company.hotelmanagementsystem.dto.request.RoomRequest;
import com.company.hotelmanagementsystem.dto.response.RoomResponse;
import com.company.hotelmanagementsystem.entity.Hotel;
import com.company.hotelmanagementsystem.entity.Room;
import com.company.hotelmanagementsystem.exception.NotFoundException;
import com.company.hotelmanagementsystem.mapper.RoomMapper;
import com.company.hotelmanagementsystem.repository.HotelRepository;
import com.company.hotelmanagementsystem.repository.RoomRepository;
import com.company.hotelmanagementsystem.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.company.hotelmanagementsystem.enums.RoomStatus.AVAILABLE;
import static com.company.hotelmanagementsystem.exception.constant.ErrorCode.NOT_FOUND;
import static com.company.hotelmanagementsystem.exception.constant.ErrorMessage.NOT_FOUND_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelRepository hotelRepository;

    @Override
    public RoomResponse createRoom(RoomRequest roomRequest) {
        Hotel hotel = hotelRepository.findById(roomRequest.getHotelId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE, NOT_FOUND));
        log.info("Creating room");
        Room room = roomMapper.toRoom(roomRequest);
        room.setHotel(hotel);
        roomRepository.save(room);

        return roomMapper.toRoomResponse(room);
    }

    @Override
    public List<RoomResponse> getAllRoomByHotel(long id) {
        log.info("Getting all rooms");
        List<Room> rooms = roomRepository.findAllByHotelId(id);

        return roomMapper.toRoomResponses(rooms);
    }

    @Override
    public RoomResponse getRoomById(long id) {
        log.info("Getting room by ID: {}", id);
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE, NOT_FOUND));

        return roomMapper.toRoomResponse(room);
    }

    @Override
    public RoomResponse updateRoom(long id, RoomRequest roomRequest) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE, NOT_FOUND));

        Hotel hotel = hotelRepository.findById(roomRequest.getHotelId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE, NOT_FOUND));

        room.setRoomNumber(roomRequest.getRoomNumber());
        room.setPrice(roomRequest.getPrice());
        room.setHotel(hotel);
        roomRepository.save(room);

        return roomMapper.toRoomResponse(room);
    }

    @Override
    public void deleteRoom(long id) {
        log.warn("deleting room by id: {}", id);
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE, NOT_FOUND));

        roomRepository.delete(room);
    }

    @Override
    public List<RoomResponse> createRoomsFromExcel(MultipartFile excelFile) {
        List<Room> rooms = new ArrayList<>();
        log.info("Creating Rooms from excel file");
        try (InputStream inputStream = excelFile.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    log.warn("Row is null.");
                    continue;
                }

                Room room = new Room();
                double hotelIdDouble = row.getCell(0).getNumericCellValue();

                if (hotelIdDouble % 1 != 0) {
                    throw new IllegalArgumentException("Hotel id is not valid");
                }

                Long hotelId = (long) hotelIdDouble;
                Hotel hotel = hotelRepository.findById(hotelId)
                        .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE, NOT_FOUND));

                room.setHotel(hotel);
                room.setRoomNumber(row.getCell(1).getStringCellValue());
                room.setPrice(row.getCell(2).getNumericCellValue());
                room.setRoomStatus(AVAILABLE);

                rooms.add(room);
            }
            log.info("saving all rooms");
            roomRepository.saveAll(rooms);
        } catch (IOException ex) {
            log.error("IO Exception happened, message: {}", ex.getMessage());
            return null;
        }

        return roomMapper.toRoomResponses(rooms);
    }
}
