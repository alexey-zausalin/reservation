package com.github.alexeyzausalin.reservation.mapper;

import com.github.alexeyzausalin.reservation.dto.HotelDTO;
import com.github.alexeyzausalin.reservation.entity.Hotel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    HotelDTO toDTO(Hotel hotel);

    Hotel toEntity(HotelDTO hotelDTO);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    void update(HotelDTO hotelDTO, @MappingTarget Hotel hotel);
}
