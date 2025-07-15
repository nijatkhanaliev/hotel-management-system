package com.company.hotelmanagementsystem.controller;

import com.company.hotelmanagementsystem.dto.request.RoomRequest;
import com.company.hotelmanagementsystem.dto.response.RoomResponse;
import com.company.hotelmanagementsystem.service.RoomService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/rooms")
@Validated
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody RoomRequest roomRequest) {
        return ResponseEntity.status(CREATED).body(roomService.createRoom(roomRequest));
    }

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRoomByHotelId(@NotNull @RequestParam Long hotelId) {
        return ResponseEntity.ok(roomService.getAllRoomByHotel(hotelId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable long id,
                                                   @Valid @RequestBody RoomRequest roomRequest) {
        return ResponseEntity.ok(roomService.updateRoom(id, roomRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @PostMapping("/excel-file")
    public ResponseEntity<List<RoomResponse>> createRoomFromExcel(
            @RequestParam(name = "file") MultipartFile file){

        return ResponseEntity.status(CREATED)
                .body(roomService.createRoomsFromExcel(file));
    }

}
