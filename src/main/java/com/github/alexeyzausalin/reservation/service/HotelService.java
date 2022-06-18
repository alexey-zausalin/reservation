package com.github.alexeyzausalin.reservation.service;

import com.github.alexeyzausalin.reservation.domain.dto.HotelView;
import com.github.alexeyzausalin.reservation.domain.dto.Page;
import com.github.alexeyzausalin.reservation.domain.dto.SearchHotelsQuery;
import com.github.alexeyzausalin.reservation.domain.mapper.HotelViewMapper;
import com.github.alexeyzausalin.reservation.domain.model.Hotel;
import com.github.alexeyzausalin.reservation.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelViewMapper hotelViewMapper;

    public HotelView getHotel(ObjectId id) {
        Hotel hotel = hotelRepository.getById(id);
        return hotelViewMapper.toHotelView(hotel);
    }

    public List<HotelView> getHotels(Iterable<ObjectId> ids) {
        List<Hotel> hotels = hotelRepository.findAllById(ids);
        return hotelViewMapper.toHotelView(hotels);
    }

    public List<HotelView> searchHotels(Page page, SearchHotelsQuery query) {
        return hotelViewMapper.toHotelView(hotelRepository.searchHotels(page, query));
    }
}
