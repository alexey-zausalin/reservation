package com.github.alexeyzausalin.reservation.service;

import com.github.alexeyzausalin.reservation.domain.dto.EditHotelRequest;
import com.github.alexeyzausalin.reservation.domain.dto.HotelView;
import com.github.alexeyzausalin.reservation.domain.dto.Page;
import com.github.alexeyzausalin.reservation.domain.dto.SearchHotelsQuery;
import com.github.alexeyzausalin.reservation.domain.mapper.HotelEditMapper;
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
    private final HotelEditMapper hotelEditMapper;
    private final HotelViewMapper hotelViewMapper;

    public HotelView create(EditHotelRequest request) {
        Hotel hotel = hotelEditMapper.create(request);

        hotel = hotelRepository.save(hotel);

        return hotelViewMapper.toHotelView(hotel);
    }

    public HotelView update(ObjectId id, EditHotelRequest request) {
        Hotel hotel = hotelRepository.getById(id);
        hotelEditMapper.update(request, hotel);

        hotel = hotelRepository.save(hotel);

        return hotelViewMapper.toHotelView(hotel);
    }

    public HotelView delete(ObjectId id) {
        Hotel hotel = hotelRepository.getById(id);

        hotelRepository.delete(hotel);

        return hotelViewMapper.toHotelView(hotel);
    }

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
