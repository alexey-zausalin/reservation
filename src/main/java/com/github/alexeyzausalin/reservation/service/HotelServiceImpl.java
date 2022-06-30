package com.github.alexeyzausalin.reservation.service;

import com.github.alexeyzausalin.reservation.dao.HotelDAO;
import com.github.alexeyzausalin.reservation.entity.Hotel;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
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
    public String createHotel(String hotel) {
        Object hotelObj = JSONValue.parse(hotel);
        JSONObject hotelJsonObject = (JSONObject) hotelObj;

        Hotel newHotel = new Hotel();

        newHotel.setName(hotelJsonObject.getAsString("name"));
        newHotel.setDescription(hotelJsonObject.getAsString("description"));

        newHotel = hotelDAO.save(newHotel);

        return newHotel.toString();
    }

    @Override
    public String updateHotel(Long id, String updateHotel) {
        Hotel hotel = hotelDAO.getById(id);
        if (hotel == null) {
            return null;
        }

        Object updateHotelObj = JSONValue.parse(updateHotel);
        JSONObject updateHotelJsonObject = (JSONObject) updateHotelObj;

        hotel.setDescription(updateHotelJsonObject.getAsString("description"));

        hotel = hotelDAO.save(hotel);

        return hotel.toString();
    }

    @Override
    public String deleteHotel(Long id) {
        Hotel hotel = hotelDAO.getById(id);
        if (hotel == null) {
            return null;
        }

        hotel = hotelDAO.delete(hotel);

        return hotel.toString();
    }

    @Override
    public String getHotel(Long id) {
        Hotel hotel = hotelDAO.getById(id);
        if (hotel == null) {
            return null;
        }

        return hotel.toString();
    }
}
