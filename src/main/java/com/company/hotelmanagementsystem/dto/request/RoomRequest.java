package com.company.hotelmanagementsystem.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRequest {
    @NotNull(message = "Hotel id must not be null")
    @Positive(message = "Hotel id must be positive")
    private Long hotelId;

    @NotBlank(message = "Room number must not be blank")
    @Size(max = 50, message = "Maximum allowed character for room number is 50")
    private String roomNumber;

    @NotNull(message = "Price must not be null")
    @Digits(integer = 10, fraction = 2)
    private Double price;
}
