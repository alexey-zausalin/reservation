package com.github.alexeyzausalin.reservation.api.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexeyzausalin.reservation.dto.HotelDTO;
import com.github.alexeyzausalin.reservation.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Service
public class HotelTestDataFactory {

    private final HotelService hotelService;

    private final ObjectMapper objectMapper;

    @Autowired
    public HotelTestDataFactory(
            HotelService hotelService,
            ObjectMapper objectMapper) {
        this.hotelService = hotelService;
        this.objectMapper = objectMapper;
    }

    public String create(String name, String description) throws JsonProcessingException {
        return create(name, description, List.of());
    }

    public String create(String name, String description, List<Long> facilityIds) throws JsonProcessingException {
        HotelDTO hotelRequest = HotelDTO
                .builder()
                .name(name)
                .description(description)
                .facilityIds(facilityIds)
                .build();

        HotelDTO hotelResponse = hotelService.createHotel(hotelRequest);

        assertNotNull(hotelResponse, "Hotel result must not be null!");
        assertNotNull(hotelResponse.id(), "Hotel id must not be null!");

        return objectMapper.writeValueAsString(hotelResponse);
    }
}
