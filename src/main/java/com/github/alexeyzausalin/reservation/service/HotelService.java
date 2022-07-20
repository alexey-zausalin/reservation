package com.github.alexeyzausalin.reservation.service;

import com.github.alexeyzausalin.reservation.dto.HotelDTO;

public interface HotelService {

    HotelDTO createHotel(HotelDTO newHotelDTO);

    HotelDTO updateHotel(Long id, HotelDTO updateHotelDTO);

    HotelDTO deleteHotel(Long id);

    HotelDTO getHotel(Long id);
}
