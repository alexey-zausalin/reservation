package com.github.alexeyzausalin.reservation.domain.mapper;

import com.github.alexeyzausalin.reservation.domain.dto.EditHotelRequest;
import com.github.alexeyzausalin.reservation.domain.model.Hotel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", uses = ObjectIdMapper.class)
public interface HotelEditMapper {

    Hotel create(EditHotelRequest request);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    void update(EditHotelRequest request, @MappingTarget Hotel hotel);
}
