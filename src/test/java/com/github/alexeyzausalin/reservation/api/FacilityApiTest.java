package com.github.alexeyzausalin.reservation.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexeyzausalin.reservation.api.data.FacilityTestDataFactory;
import com.github.alexeyzausalin.reservation.domain.dto.EditFacilityRequest;
import com.github.alexeyzausalin.reservation.domain.dto.FacilityView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.github.alexeyzausalin.reservation.util.JsonHelper.fromJson;
import static com.github.alexeyzausalin.reservation.util.JsonHelper.toJson;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FacilityApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FacilityTestDataFactory facilityTestDataFactory;

    @Test
    public void testCreateSuccess() throws Exception {
        EditFacilityRequest goodRequest = new EditFacilityRequest(
                "Internet",
                "WiFi is available in the hotel rooms and is free of charge."
        );

        MvcResult createResult = this.mockMvc
                .perform(post("/api/v1/facilities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, goodRequest)))
                .andExpect(status().isOk())
                .andReturn();

        FacilityView facilityView = fromJson(objectMapper, createResult.getResponse().getContentAsString(), FacilityView.class);
        assertNotNull(facilityView.id(), "Facility id must not be null!");
        assertEquals(goodRequest.name(), facilityView.name(), "Facility name update isn't applied!");
    }

    @Test
    public void testCreateFail() throws Exception {
        EditFacilityRequest badRequest = new EditFacilityRequest();

        this.mockMvc
                .perform(post("/api/v1/facilities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, badRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Method argument validation failed")));
    }

    @Test
    public void testEditSuccess() throws Exception {
        FacilityView facilityView = facilityTestDataFactory.createFacility(
                "Internet",
                "WiFi is available in the hotel rooms and is free of charge."
        );

        EditFacilityRequest updateRequest = new EditFacilityRequest(
                "New Internet",
                "New WiFi is available in the hotel rooms and is free of charge."
        );

        MvcResult updateResult = this.mockMvc
                .perform(put(String.format("/api/v1/facilities/%s", facilityView.id()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, updateRequest)))
                .andExpect(status().isOk())
                .andReturn();
        FacilityView newFacilityView = fromJson(objectMapper, updateResult.getResponse().getContentAsString(), FacilityView.class);

        assertEquals(updateRequest.group(), newFacilityView.group(), "Facility group update isn't applied!");
        assertEquals(updateRequest.name(), newFacilityView.name(), "name description update isn't applied!");
    }

    @Test
    public void testEditFailBadRequest() throws Exception {
        FacilityView facilityView = facilityTestDataFactory.createFacility(
                "Internet",
                "WiFi is available in the hotel rooms and is free of charge."
        );

        EditFacilityRequest updateRequest = new EditFacilityRequest();

        this.mockMvc
                .perform(put(String.format("/api/v1/facilities/%s", facilityView.id()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Method argument validation failed")));
    }

    @Test
    public void testEditFailNotFound() throws Exception {
        EditFacilityRequest updateRequest = new EditFacilityRequest(
                "Internet",
                "WiFi is available in the hotel rooms and is free of charge."
        );

        this.mockMvc
                .perform(put(String.format("/api/v1/facilities/%s", "5f07c259ffb98843e36a2aa9"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, updateRequest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity Facility with id 5f07c259ffb98843e36a2aa9 not found")));
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        FacilityView facilityView = facilityTestDataFactory.createFacility(
                "Internet",
                "WiFi is available in the hotel rooms and is free of charge."
        );
        this.mockMvc
                .perform(delete(String.format("/api/v1/facilities/%s", facilityView.id())))
                .andExpect(status().isOk());

        this.mockMvc
                .perform(get(String.format("/api/v1/facilities/%s", facilityView.id())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteFailNotFound() throws Exception {
        this.mockMvc
                .perform(delete(String.format("/api/v1/facilities/%s", "5f07c259ffb98843e36a2aa9")))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity Facility with id 5f07c259ffb98843e36a2aa9 not found")));
    }

    @Test
    public void testGetSuccess() throws Exception {
        FacilityView facilityView = facilityTestDataFactory.createFacility(
                "Internet",
                "WiFi is available in the hotel rooms and is free of charge."
        );

        MvcResult getResult = this.mockMvc
                .perform(get(String.format("/api/v1/facilities/%s", facilityView.id())))
                .andExpect(status().isOk())
                .andReturn();

        FacilityView getFacilityView = fromJson(objectMapper, getResult.getResponse().getContentAsString(), FacilityView.class);

        assertEquals(facilityView.id(), getFacilityView.id(), "Facility ids must be equal!");
    }

    @Test
    public void testGetNotFound() throws Exception {
        this.mockMvc
                .perform(get(String.format("/api/v1/facilities/%s", "5f07c259ffb98843e36a2aa9")))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity Facility with id 5f07c259ffb98843e36a2aa9 not found")));
    }
}
