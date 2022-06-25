package com.github.alexeyzausalin.reservation.domain.mapper;

import com.github.alexeyzausalin.reservation.domain.dto.EditFacilityRequest;
import com.github.alexeyzausalin.reservation.domain.model.Facility;
import org.mapstruct.*;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", uses = ObjectIdMapper.class)
public interface FacilityEditMapper {

    Facility create(EditFacilityRequest request);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    void update(EditFacilityRequest request, @MappingTarget Facility facility);
}
