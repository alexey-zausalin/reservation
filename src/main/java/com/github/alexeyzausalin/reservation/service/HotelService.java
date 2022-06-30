package com.github.alexeyzausalin.reservation.service;

import com.github.alexeyzausalin.reservation.dto.HotelDTO;

public interface HotelService {

    HotelDTO createHotel(HotelDTO hotel);

    HotelDTO updateHotel(Long id, HotelDTO updateHotel);

    HotelDTO deleteHotel(Long id);

    HotelDTO getHotel(Long id);
}
