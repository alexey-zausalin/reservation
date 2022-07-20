package com.github.alexeyzausalin.reservation.mapper;

import com.github.alexeyzausalin.reservation.dto.FacilityDTO;
import com.github.alexeyzausalin.reservation.entity.Facility;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface FacilityMapper {

    FacilityDTO toDTO(Facility facility);

    List<FacilityDTO> toDTO(Collection<Facility> facilities);

    Facility toEntity(FacilityDTO facilityDTO);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    void update(FacilityDTO facilityDTO, @MappingTarget Facility facility);
}
