package com.github.alexeyzausalin.reservation.service;

import com.github.alexeyzausalin.reservation.dto.FacilityDTO;
import com.github.alexeyzausalin.reservation.entity.Facility;
import com.github.alexeyzausalin.reservation.entity.Hotel;
import com.github.alexeyzausalin.reservation.mapper.FacilityMapper;
import com.github.alexeyzausalin.reservation.repository.FacilityRepository;
import com.github.alexeyzausalin.reservation.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("facilityService")
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;

    private final HotelRepository hotelRepository;

    private final FacilityMapper facilityMapper;

    @Autowired
    public FacilityServiceImpl(
            FacilityRepository facilityRepository,
            HotelRepository hotelRepository,
            FacilityMapper facilityMapper) {
        this.facilityRepository = facilityRepository;
        this.hotelRepository = hotelRepository;
        this.facilityMapper = facilityMapper;
    }

    @Override
    @Transactional
    public FacilityDTO createFacility(FacilityDTO newFacilityDTO) {
        Facility newFacility = new Facility();

        facilityMapper.update(newFacilityDTO, newFacility);
        newFacility = facilityRepository.save(newFacility);

        return facilityMapper.toDTO(newFacility);
    }

    @Override
    public List<FacilityDTO> getHotelFacilities(Long hotelId) {
        Hotel hotel = hotelRepository.getById(hotelId);

        return facilityMapper.toDTO(hotel.getFacilities());
    }
}
