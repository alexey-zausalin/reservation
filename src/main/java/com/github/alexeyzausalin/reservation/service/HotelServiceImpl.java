package com.github.alexeyzausalin.reservation.service;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service("hotelService")
public class HotelServiceImpl implements HotelService {

    private final AtomicLong counter = new AtomicLong();

    private final Map<Long, String> hotels = new HashMap<>();

    @Override
    public String createHotel(String hotel) {
        Long newId = counter.incrementAndGet();

        Object hotelObj = JSONValue.parse(hotel);
        JSONObject hotelJsonObject = (JSONObject) hotelObj;

        String newHotel = String.format(
                "{\"id\":\"%d\",\"name\":\"%s\",\"description\":\"%s\"}",
                newId,
                hotelJsonObject.getAsString("name"),
                hotelJsonObject.getAsString("description"));

        hotels.put(newId, newHotel);

        return newHotel;
    }

    @Override
    public String updateHotel(Long id, String updateHotel) {
        String hotel = hotels.get(id);

        Object hotelObj = JSONValue.parse(hotel);
        JSONObject hotelJsonObject = (JSONObject) hotelObj;

        Object updateHotelObj = JSONValue.parse(updateHotel);
        JSONObject updateHotelJsonObject = (JSONObject) updateHotelObj;

        hotelJsonObject.put("description", updateHotelJsonObject.getAsString("description"));
        hotel = hotelJsonObject.toString();

        hotels.put(id, hotel);

        return hotel;
    }

    @Override
    public String deleteHotel(Long id) {
        return hotels.remove(id);
    }

    @Override
    public String getHotel(Long id) {
        return hotels.get(id);
    }
}
