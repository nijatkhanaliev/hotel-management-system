package com.company.hotelmanagementsystem.service;

import com.company.hotelmanagementsystem.dto.response.HotelResponse;
import com.company.hotelmanagementsystem.entity.Hotel;
import com.company.hotelmanagementsystem.exception.DuplicateLanguageException;
import com.company.hotelmanagementsystem.exception.NotFoundException;
import com.company.hotelmanagementsystem.mapper.HotelDescriptionMapper;
import com.company.hotelmanagementsystem.mapper.HotelDescriptionMapperImpl;
import com.company.hotelmanagementsystem.mapper.HotelMapper;
import com.company.hotelmanagementsystem.mapper.HotelMapperImpl;
import com.company.hotelmanagementsystem.repository.HotelRepository;
import com.company.hotelmanagementsystem.service.impl.HotelServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;

import static com.company.hotelmanagementsystem.constants.TestHotelConstants.HOTEL_ID;
import static com.company.hotelmanagementsystem.constants.TestHotelConstants.HOTEL_PROJECTION;
import static com.company.hotelmanagementsystem.constants.TestHotelConstants.HOTEL_REQUEST;
import static com.company.hotelmanagementsystem.constants.TestHotelConstants.HOTEL_REQUEST_DUPLICATE_DESC;
import static com.company.hotelmanagementsystem.constants.TestHotelConstants.HOTEL_RESPONSE_ENG;
import static com.company.hotelmanagementsystem.constants.TestHotelConstants.USER_LANG_ENG;
import static com.company.hotelmanagementsystem.exception.constant.ErrorCode.DUPLICATE_LANGUAGE;
import static com.company.hotelmanagementsystem.exception.constant.ErrorCode.NOT_FOUND;
import static com.company.hotelmanagementsystem.exception.constant.ErrorMessage.DUPLICATE_LANGUAGE_MESSAGE;
import static com.company.hotelmanagementsystem.exception.constant.ErrorMessage.NOT_FOUND_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    @Spy
    HotelMapper hotelMapper = new HotelMapperImpl();

    @Mock
    HotelDescriptionMapper hotelDescriptionMapper = new HotelDescriptionMapperImpl();

    @Mock
    HotelRepository hotelRepository;

    @InjectMocks
    HotelServiceImpl hotelService;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field field = hotelMapper.getClass().getDeclaredField("hotelDescriptionMapper");
        field.setAccessible(true);
        field.set(hotelMapper, hotelDescriptionMapper);
    }

    @Test
    void test_getHotelById_returnSuccess() {
        when(hotelRepository.findHotelWithDescription(HOTEL_ID, USER_LANG_ENG))
                .thenReturn(Optional.of(HOTEL_PROJECTION));

        HotelResponse hotelResponse = hotelService.getHotelById(HOTEL_ID, USER_LANG_ENG);

        assertEquals(hotelResponse.getId(), HOTEL_PROJECTION.getId());
        assertEquals(hotelResponse.getName(), HOTEL_PROJECTION.getName());
        assertEquals(hotelResponse.getDescription().getDescription(),
                HOTEL_PROJECTION.getDescription());
        assertEquals(hotelResponse.getLocation(), HOTEL_PROJECTION.getLocation());
        assertEquals(hotelResponse.getCreatedAt().get(ChronoField.CLOCK_HOUR_OF_DAY),
                HOTEL_PROJECTION.getCreatedAt().get(ChronoField.CLOCK_HOUR_OF_DAY));

        verify(hotelRepository, times(1))
                .findHotelWithDescription(HOTEL_ID, USER_LANG_ENG);

        verify(hotelMapper, times(1))
                .toHotelResponseFromProject(HOTEL_PROJECTION);
    }

    @Test
    void test_hotelGetById_throwsNotFoundException() {
        when(hotelRepository.findHotelWithDescription(HOTEL_ID, USER_LANG_ENG))
                .thenReturn(Optional.empty());

        NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () -> {
            hotelService.getHotelById(HOTEL_ID, USER_LANG_ENG);
        });

        assertEquals(ex.getErrorCode(), NOT_FOUND);
        assertEquals(ex.getErrorMessage(), NOT_FOUND_MESSAGE);

        verify(hotelRepository, times(1))
                .findHotelWithDescription(HOTEL_ID, USER_LANG_ENG);

        verifyNoInteractions(hotelMapper);
    }

    @Test
    void test_hotelGetAll() {
        when(hotelRepository.findAllWithDescriptions(USER_LANG_ENG))
                .thenReturn(List.of(HOTEL_PROJECTION));

        List<HotelResponse> allHotel = hotelService.getAllHotel(USER_LANG_ENG);

        assertEquals(allHotel.size(), 1);
        assertEquals(allHotel.getFirst().getId(), HOTEL_PROJECTION.getId());
        assertEquals(allHotel.getFirst().getName(), HOTEL_PROJECTION.getName());
        assertEquals(allHotel.getFirst().getLocation(), HOTEL_PROJECTION.getLocation());
        assertEquals(allHotel.getFirst().getDescription().getDescription(),
                HOTEL_PROJECTION.getDescription());

        verify(hotelRepository, times(1)).findAllWithDescriptions(USER_LANG_ENG);
    }

    @Test
    void test_createHotel_returnSuccess() {
        when(hotelRepository.save(any(Hotel.class))).thenReturn(null);
        doReturn(HOTEL_RESPONSE_ENG).when(hotelMapper).toHotelResponse(any(Hotel.class));
        HotelResponse hotelResponseEng = hotelService.createHotel(HOTEL_REQUEST, USER_LANG_ENG);

        assertEquals(hotelResponseEng.getName(), HOTEL_REQUEST.getName());
        assertEquals(hotelResponseEng.getLocation(), HOTEL_REQUEST.getLocation());
        assertEquals(hotelResponseEng.getDescription().getDescription(),
                HOTEL_REQUEST.getHotelDescriptionRequests().getLast().getDescription());

        verify(hotelRepository, times(1)).save(any(Hotel.class));
        verify(hotelMapper, times(1)).toHotelResponse(any(Hotel.class));
    }

    @Test
    void test_createHotel_throwsDuplicateLanguageException() {
        DuplicateLanguageException ex = assertThrows(DuplicateLanguageException.class, () -> {
            hotelService.createHotel(
                    HOTEL_REQUEST_DUPLICATE_DESC, USER_LANG_ENG
            );
        });

        assertEquals(ex.getErrorCode(), DUPLICATE_LANGUAGE);
        assertEquals(ex.getErrorMessage(), DUPLICATE_LANGUAGE_MESSAGE);

        verifyNoInteractions(hotelRepository);
        verifyNoInteractions(hotelMapper);
    }

}
