package com.github.alexeyzausalin.reservation.service;

import com.github.alexeyzausalin.reservation.entity.Facility;
import com.github.alexeyzausalin.reservation.repository.FacilityRepository;
import com.github.alexeyzausalin.reservation.repository.HotelRepository;
import com.github.alexeyzausalin.reservation.dto.HotelDTO;
import com.github.alexeyzausalin.reservation.entity.Hotel;
import com.github.alexeyzausalin.reservation.mapper.HotelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service("hotelService")
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    private final FacilityRepository facilityRepository;

    private final HotelMapper hotelMapper;

    @Autowired
    public HotelServiceImpl(
            HotelRepository hotelRepository,
            FacilityRepository facilityRepository,
            HotelMapper hotelMapper) {
        this.hotelRepository = hotelRepository;
        this.facilityRepository = facilityRepository;
        this.hotelMapper = hotelMapper;
    }

    @Override
    @Transactional
    public HotelDTO createHotel(HotelDTO newHotelDTO) {
        Hotel newHotel = new Hotel();

        hotelMapper.update(newHotelDTO, newHotel);
        updateFacilities(newHotel, newHotelDTO.facilityIds());
        newHotel = hotelRepository.save(newHotel);

        return hotelMapper.toDTO(newHotel);
    }

    @Override
    @Transactional
    public HotelDTO updateHotel(Long id, HotelDTO updateHotelDTO) {
        Hotel hotel = hotelRepository.getById(id);

        hotelMapper.update(updateHotelDTO, hotel);
        if (!CollectionUtils.isEmpty(updateHotelDTO.facilityIds())) {
            updateFacilities(hotel, updateHotelDTO.facilityIds());
        }
        hotel = hotelRepository.save(hotel);

        return hotelMapper.toDTO(hotel);
    }

    @Override
    @Transactional
    public HotelDTO deleteHotel(Long id) {
        Hotel hotel = hotelRepository.getById(id);

        hotelRepository.delete(hotel);

        return hotelMapper.toDTO(hotel);
    }

    @Override
    public HotelDTO getHotel(Long id) {
        Hotel hotel = hotelRepository.getById(id);

        return hotelMapper.toDTO(hotel);
    }

    private void updateFacilities(Hotel hotel, List<Long> facilityIds) {
        List<Facility> facilities = facilityRepository.findAllById(facilityIds);
        facilities.forEach(hotel::addFacility);

        //TODO: delete unnecessary facilities
    }
}
