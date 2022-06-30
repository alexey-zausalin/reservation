package com.github.alexeyzausalin.reservation.api.data;

import com.github.alexeyzausalin.reservation.service.HotelService;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Service
public class HotelTestDataFactory {

    private final HotelService hotelService;

    @Autowired
    public HotelTestDataFactory(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    public String create(String name, String description) {
        String hotelRequest = String.format("{\"name\":\"%s\",\"description\":\"%s\"}", name, description);

        String hotelView = hotelService.createHotel(hotelRequest);

        assertNotNull(hotelView, "Hotel result must not be null!");

        Object obj = JSONValue.parse(hotelView);
        JSONObject jsonObject = (JSONObject) obj;

        Long id = Long.parseLong(jsonObject.getAsString("id"));

        assertNotNull(id, "Hotel id must not be null!");

        return hotelView;

    }
}
