package com.github.alexeyzausalin.reservation.service;

import com.github.alexeyzausalin.reservation.domain.dto.EditFacilityRequest;
import com.github.alexeyzausalin.reservation.domain.dto.FacilityView;
import com.github.alexeyzausalin.reservation.domain.dto.Page;
import com.github.alexeyzausalin.reservation.domain.dto.SearchFacilitiesQuery;
import com.github.alexeyzausalin.reservation.domain.mapper.FacilityEditMapper;
import com.github.alexeyzausalin.reservation.domain.mapper.FacilityViewMapper;
import com.github.alexeyzausalin.reservation.domain.model.Facility;
import com.github.alexeyzausalin.reservation.domain.model.Hotel;
import com.github.alexeyzausalin.reservation.repository.FacilityRepository;
import com.github.alexeyzausalin.reservation.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;

    private final HotelRepository hotelRepository;

    private final FacilityEditMapper facilityEditMapper;

    private final FacilityViewMapper facilityViewMapper;

    @Transactional
    public FacilityView create(EditFacilityRequest request) {
        Facility facility = facilityEditMapper.create(request);

        facility = facilityRepository.save(facility);

        return facilityViewMapper.toFacilityView(facility);
    }

    @Transactional
    public FacilityView update(ObjectId id, EditFacilityRequest request) {
        Facility facility = facilityRepository.getById(id);
        facilityEditMapper.update(request, facility);

        facility = facilityRepository.save(facility);

        return facilityViewMapper.toFacilityView(facility);
    }

    @Transactional
    public FacilityView delete(ObjectId id) {
        Facility facility = facilityRepository.getById(id);

        facilityRepository.delete(facility);
        //TODO: delete from all hotels or block from deleting while hotels with the facility exists

        return facilityViewMapper.toFacilityView(facility);
    }

    public FacilityView getFacility(ObjectId id) {
        return facilityViewMapper.toFacilityView(facilityRepository.getById(id));
    }

    public List<FacilityView> getFacilities(Iterable<ObjectId> ids) {
        return facilityViewMapper.toFacilityView(facilityRepository.findAllById(ids));
    }

    public List<FacilityView> getHotelFacilities(ObjectId hotelId) {
        Hotel hotel = hotelRepository.getById(hotelId);
        return facilityViewMapper.toFacilityView(facilityRepository.findAllById(hotel.getFacilityIds()));
    }

    public List<FacilityView> searchFacilities(Page page, SearchFacilitiesQuery query) {
        return facilityViewMapper.toFacilityView(facilityRepository.searchFacilities(page, query));
    }
}
