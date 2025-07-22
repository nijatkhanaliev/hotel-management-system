package com.company.hotelmanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelDescriptionRequest {

    @NotBlank(message = "Language cannot be blank")
    @Size(max = 20,message = "Language can contains 5 character maximum")
    private String language;

    @NotBlank(message = "Hotel description cannot be blank")
    private String description;
}
