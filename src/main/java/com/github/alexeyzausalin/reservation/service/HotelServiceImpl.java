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

        return convertHotelEntityToHotelDTO(newHotel);
    }

    @Override
    public HotelDTO updateHotel(Long id, HotelDTO updateHotel) {
        Hotel hotel = hotelDAO.getById(id);
        if (hotel == null) {
            return null;
        }

        hotel.setName(updateHotel.name());
        hotel.setDescription(updateHotel.description());

        hotel = hotelDAO.save(hotel);

        return convertHotelEntityToHotelDTO(hotel);
    }

    @Override
    public HotelDTO deleteHotel(Long id) {
        Hotel hotel = hotelDAO.getById(id);
        if (hotel == null) {
            return null;
        }

        hotel = hotelDAO.delete(hotel);

        return convertHotelEntityToHotelDTO(hotel);
    }

    @Override
    public HotelDTO getHotel(Long id) {
        Hotel hotel = hotelDAO.getById(id);
        if (hotel == null) {
            return null;
        }

        return convertHotelEntityToHotelDTO(hotel);
    }

    private HotelDTO convertHotelEntityToHotelDTO(Hotel hotelEntity) {
        return HotelDTO
                .builder()
                .id(hotelEntity.getId())
                .name(hotelEntity.getName())
                .description(hotelEntity.getDescription())
                .build();
    }
}
