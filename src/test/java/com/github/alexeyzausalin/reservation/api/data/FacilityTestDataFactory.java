package com.github.alexeyzausalin.reservation.api.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexeyzausalin.reservation.dto.FacilityDTO;
import com.github.alexeyzausalin.reservation.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Service
public class FacilityTestDataFactory {
    private final FacilityService facilityService;

    private final ObjectMapper objectMapper;

    @Autowired
    public FacilityTestDataFactory(
            FacilityService facilityService,
            ObjectMapper objectMapper) {
        this.facilityService = facilityService;
        this.objectMapper = objectMapper;
    }

    public String create(String group, String name) throws JsonProcessingException {
        FacilityDTO facilityRequest = FacilityDTO
                .builder()
                .group(group)
                .name(name)
                .build();

        FacilityDTO facilityResponse = facilityService.createFacility(facilityRequest);

        assertNotNull(facilityResponse, "Facility result must not be null!");
        assertNotNull(facilityResponse.id(), "Facility id must not be null!");

        return objectMapper.writeValueAsString(facilityResponse);
    }
}
