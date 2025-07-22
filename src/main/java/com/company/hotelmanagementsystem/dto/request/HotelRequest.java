package com.company.hotelmanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HotelRequest {

    @NotBlank(message = "Hotel name must not be blank")
    @Size(max = 255, message = "Hotel name must be less than 255")
    private String name;

    @Size(max = 255, message = "Hotel name must be less than 255")
    private String location;

    @NotNull(message = "Hotel description cannot be null")
    private List<HotelDescriptionRequest> hotelDescriptionRequests;
}
