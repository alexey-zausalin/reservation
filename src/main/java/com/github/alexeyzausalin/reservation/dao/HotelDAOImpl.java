package com.github.alexeyzausalin.reservation.dao;

import com.github.alexeyzausalin.reservation.entity.Hotel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository("hotelDAO")
public class HotelDAOImpl implements HotelDAO {

    private final AtomicLong counter = new AtomicLong();
    private final Map<Long, Hotel> hotels = new HashMap<>();

    @Override
    public Hotel getById(Long id) {
        return hotels.get(id);
    }

    @Override
    public Hotel save(Hotel hotel) {
        if (hotel.getId() == null) {
            Long newId = counter.incrementAndGet();
            hotel.setId(newId);
        }

        hotels.put(hotel.getId(), hotel);

        return hotel;
    }

    @Override
    public Hotel delete(Hotel hotel) {
        return hotels.remove(hotel.getId());
    }
}
