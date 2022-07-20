package com.github.alexeyzausalin.reservation.service;

import com.github.alexeyzausalin.reservation.dto.FacilityDTO;

import java.util.List;

public interface FacilityService {

    FacilityDTO createFacility(FacilityDTO newFacilityDTO);

    List<FacilityDTO> getHotelFacilities(Long hotelId);
}
