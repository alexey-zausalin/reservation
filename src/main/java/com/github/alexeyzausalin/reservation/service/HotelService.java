package com.github.alexeyzausalin.reservation.service;

public interface HotelService {

    String createHotel(String hotel);

    String updateHotel(Long id, String updateHotel);

    String deleteHotel(Long id);

    String getHotel(Long id);
}
