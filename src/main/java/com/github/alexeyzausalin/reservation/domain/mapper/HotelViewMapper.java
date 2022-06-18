package com.github.alexeyzausalin.reservation.domain.mapper;

import com.github.alexeyzausalin.reservation.domain.dto.HotelView;
import com.github.alexeyzausalin.reservation.domain.model.Hotel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ObjectIdMapper.class)
public interface HotelViewMapper {

    HotelView toHotelView(Hotel hotel);

    List<HotelView> toHotelView(List<Hotel> hotels);
}
