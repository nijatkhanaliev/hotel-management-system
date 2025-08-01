package com.company.hotelmanagementsystem.controller;

import com.company.hotelmanagementsystem.dto.request.HotelRequest;
import com.company.hotelmanagementsystem.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.company.hotelmanagementsystem.constants.TestHotelConstants.HOTEL_REQUEST;
import static com.company.hotelmanagementsystem.constants.TestHotelConstants.HOTEL_RESPONSE_ENG;
import static com.company.hotelmanagementsystem.constants.TestHotelConstants.USER_LANG_ENG;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HotelController.class)
public class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void test_hotelCreate_returnSuccess() throws Exception {
        when(hotelService.createHotel(any(HotelRequest.class), anyString()))
                .thenReturn(HOTEL_RESPONSE_ENG);

        mockMvc.perform(
                        post("/v1/hotels")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("lang", USER_LANG_ENG)
                                .content(objectMapper.writeValueAsString(HOTEL_REQUEST))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Test Hotel")))
                .andExpect(jsonPath("$.location", is("Test")))
                .andExpect(jsonPath("$.id", is(1)));

    }

    @Test
    void test_hotelGetById_returnSuccess() throws Exception {
        when(hotelService.getHotelById(anyLong(),anyString())).thenReturn(HOTEL_RESPONSE_ENG);

        mockMvc.perform(
                get("/v1/hotels/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("lang", USER_LANG_ENG)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Hotel")))
                .andExpect(jsonPath("$.location", is("Test")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void test_hotelGetAll_returnSuccess() throws Exception {
        when(hotelService.getAllHotel(anyString())).thenReturn(List.of(HOTEL_RESPONSE_ENG));

        mockMvc.perform(
                get("/v1/hotels")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("lang", USER_LANG_ENG)
        )
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Test Hotel")))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].location", is("Test")));
    }

}
