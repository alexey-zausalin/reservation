package com.github.alexeyzausalin.reservation.api.data;

import com.github.alexeyzausalin.reservation.domain.dto.EditHotelRequest;
import com.github.alexeyzausalin.reservation.domain.dto.HotelView;
import com.github.alexeyzausalin.reservation.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Service
public class HotelTestDataFactory {

    @Autowired
    private HotelService hotelService;

    public HotelView createHotel(
            String pageUrl,
            String name,
            String description,
            List<String> facilities,
            List<String> rules,
            String address,
            String latitude,
            String longitude,
            String hotelType) {
        EditHotelRequest createRequest = new EditHotelRequest(
                pageUrl,
                name,
                description,
                facilities,
                rules,
                address,
                latitude,
                longitude,
                hotelType
        );

        HotelView hotelView = hotelService.create(createRequest);

        assertNotNull(hotelView.id(), "Hotel id must not be null!");
        assertEquals(name, hotelView.name(), "Hotel name update isn't applied!");

        return hotelView;
    }

    public HotelView createHotel(
            String name,
            String address,
            String hotelType) {
        return createHotel(null, name, null, null, null, address, null, null, hotelType);
    }
}
