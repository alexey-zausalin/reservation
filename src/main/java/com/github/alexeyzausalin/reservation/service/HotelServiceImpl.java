package com.github.alexeyzausalin.reservation.service;

import com.github.alexeyzausalin.reservation.dao.HotelDAO;
import com.github.alexeyzausalin.reservation.dto.HotelDTO;
import com.github.alexeyzausalin.reservation.entity.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("hotelService")
public class HotelServiceImpl implements HotelService {

    private final HotelDAO hotelDAO;

    @Autowired
    public HotelServiceImpl(HotelDAO hotelDAO) {
        this.hotelDAO = hotelDAO;
    }

    @Override
    public HotelDTO createHotel(HotelDTO hotel) {
        Hotel newHotel = new Hotel();

        newHotel.setName(hotel.name());
        newHotel.setDescription(hotel.description());

        newHotel = hotelDAO.save(newHotel);

        return HotelDTO
                .builder()
                .id(newHotel.getId())
                .name(newHotel.getName())
                .description(newHotel.getDescription())
                .build();
    }

    @Override
    public HotelDTO updateHotel(Long id, HotelDTO updateHotel) {
        Hotel hotel = hotelDAO.getById(id);
        if (hotel == null) {
            return null;
        }

        hotel.setDescription(updateHotel.description());

        hotel = hotelDAO.save(hotel);

        return HotelDTO
                .builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .build();
    }

    @Override
    public HotelDTO deleteHotel(Long id) {
        Hotel hotel = hotelDAO.getById(id);
        if (hotel == null) {
            return null;
        }

        hotel = hotelDAO.delete(hotel);

        return HotelDTO
                .builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .build();
    }

    @Override
    public HotelDTO getHotel(Long id) {
        Hotel hotel = hotelDAO.getById(id);
        if (hotel == null) {
            return null;
        }

        return HotelDTO
                .builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .build();
    }
}
