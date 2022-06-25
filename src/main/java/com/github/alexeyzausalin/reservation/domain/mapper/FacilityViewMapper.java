package com.github.alexeyzausalin.reservation.domain.mapper;

import com.github.alexeyzausalin.reservation.domain.dto.FacilityView;
import com.github.alexeyzausalin.reservation.domain.model.Facility;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ObjectIdMapper.class)
public interface FacilityViewMapper {

    FacilityView toFacilityView(Facility facility);

    List<FacilityView> toFacilityView(List<Facility> facilities);
}
