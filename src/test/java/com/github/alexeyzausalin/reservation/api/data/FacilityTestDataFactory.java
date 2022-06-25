package com.github.alexeyzausalin.reservation.api.data;

import com.github.alexeyzausalin.reservation.domain.dto.EditFacilityRequest;
import com.github.alexeyzausalin.reservation.domain.dto.FacilityView;
import com.github.alexeyzausalin.reservation.service.FacilityService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Service
public class FacilityTestDataFactory {

    @Autowired
    private FacilityService facilityService;

    public FacilityView createFacility(String group, String name) {
        EditFacilityRequest createRequest = new EditFacilityRequest(group, name);

        FacilityView facilityView = facilityService.create(createRequest);

        assertNotNull(facilityView.id(), "Facility id must not be null!");
        assertEquals(name, facilityView.name(), "Facility name update isn't applied!");

        return facilityView;
    }

    public void deleteFacility(String id) {
        facilityService.delete(new ObjectId(id));
    }
}
