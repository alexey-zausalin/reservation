package com.github.alexeyzausalin.reservation.service;

import com.github.alexeyzausalin.reservation.repository.HotelRepository;
import com.github.alexeyzausalin.reservation.dto.HotelDTO;
import com.github.alexeyzausalin.reservation.entity.Hotel;
import com.github.alexeyzausalin.reservation.mapper.HotelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("hotelService")
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    private final HotelMapper hotelMapper;

    @Autowired
    public HotelServiceImpl(
            HotelRepository hotelRepository,
            HotelMapper hotelMapper) {
        this.hotelRepository = hotelRepository;
        this.hotelMapper = hotelMapper;
    }

    @Override
    public HotelDTO createHotel(HotelDTO newHotelDTO) {
        Hotel newHotel = new Hotel();

        hotelMapper.update(newHotelDTO, newHotel);
        newHotel = hotelRepository.save(newHotel);

        return hotelMapper.toDTO(newHotel);
    }

    @Override
    public HotelDTO updateHotel(Long id, HotelDTO updateHotelDTO) {
        Hotel hotel = hotelRepository.getById(id);

        hotelMapper.update(updateHotelDTO, hotel);
        hotel = hotelRepository.save(hotel);

        return hotelMapper.toDTO(hotel);
    }

    @Override
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
}
