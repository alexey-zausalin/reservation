package com.github.alexeyzausalin.reservation.dao;

import com.github.alexeyzausalin.reservation.entity.Hotel;

public interface HotelDAO {

    Hotel getById(Long id);

    Hotel save(Hotel hotel);

    Hotel delete(Hotel hotel);
}
