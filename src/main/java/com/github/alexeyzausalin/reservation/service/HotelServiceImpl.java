package com.github.alexeyzausalin.reservation.service;

import com.github.alexeyzausalin.reservation.dao.HotelDAO;
import com.github.alexeyzausalin.reservation.dto.HotelDTO;
import com.github.alexeyzausalin.reservation.entity.Hotel;
import com.github.alexeyzausalin.reservation.mapper.HotelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("hotelService")
public class HotelServiceImpl implements HotelService {

    private final HotelDAO hotelDAO;

    private final HotelMapper hotelMapper;

    @Autowired
    public HotelServiceImpl(
            HotelDAO hotelDAO,
            HotelMapper hotelMapper) {
        this.hotelDAO = hotelDAO;
        this.hotelMapper = hotelMapper;
    }

    @Override
    public HotelDTO createHotel(HotelDTO newHotelDTO) {
        Hotel newHotel = new Hotel();

        hotelMapper.update(newHotelDTO, newHotel);
        newHotel = hotelDAO.save(newHotel);

        return hotelMapper.toDTO(newHotel);
    }

    @Override
    public HotelDTO updateHotel(Long id, HotelDTO updateHotelDTO) {
        Hotel hotel = hotelDAO.getById(id);
        if (hotel == null) {
            return null;
        }

        hotelMapper.update(updateHotelDTO, hotel);
        hotel = hotelDAO.save(hotel);

        return hotelMapper.toDTO(hotel);
    }

    @Override
    public HotelDTO deleteHotel(Long id) {
        Hotel hotel = hotelDAO.getById(id);
        if (hotel == null) {
            return null;
        }

        hotel = hotelDAO.delete(hotel);

        return hotelMapper.toDTO(hotel);
    }

    @Override
    public HotelDTO getHotel(Long id) {
        Hotel hotel = hotelDAO.getById(id);
        if (hotel == null) {
            return null;
        }

        return hotelMapper.toDTO(hotel);
    }
}
