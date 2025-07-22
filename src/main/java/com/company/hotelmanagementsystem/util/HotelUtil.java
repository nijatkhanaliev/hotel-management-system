package com.company.hotelmanagementsystem.util;

import com.company.hotelmanagementsystem.dto.request.HotelDescriptionRequest;
import com.company.hotelmanagementsystem.dto.request.HotelRequest;
import com.company.hotelmanagementsystem.exception.DuplicateLanguageException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.company.hotelmanagementsystem.exception.constant.ErrorCode.DUPLICATE_LANGUAGE;
import static com.company.hotelmanagementsystem.exception.constant.ErrorMessage.DUPLICATE_LANGUAGE_MESSAGE;

@Slf4j
public class HotelUtil {

    public static void validateUniqueLanguage(HotelRequest hotelRequest){
        log.info("Validating Unique language for hotel description");
        List<HotelDescriptionRequest> hotelDescriptionRequests = hotelRequest
                .getHotelDescriptionRequests();

        List<String> languages = new ArrayList<>();

        for(HotelDescriptionRequest request : hotelDescriptionRequests){
            if(languages.contains(request.getLanguage())){
                throw new DuplicateLanguageException(DUPLICATE_LANGUAGE_MESSAGE, DUPLICATE_LANGUAGE);
            }
            languages.add(request.getLanguage());
        }
    }

}
