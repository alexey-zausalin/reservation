package com.github.alexeyzausalin.reservation.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexeyzausalin.reservation.api.data.FacilityTestDataFactory;
import com.github.alexeyzausalin.reservation.api.data.HotelTestDataFactory;
import com.github.alexeyzausalin.reservation.domain.dto.EditHotelRequest;
import com.github.alexeyzausalin.reservation.domain.dto.FacilityView;
import com.github.alexeyzausalin.reservation.domain.dto.HotelView;
import com.github.alexeyzausalin.reservation.domain.dto.ListResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

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
public class HotelApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HotelTestDataFactory hotelTestDataFactory;

    @Autowired
    private FacilityTestDataFactory facilityTestDataFactory;

    @Test
    public void testCreateSuccess() throws Exception {
        FacilityView facilityView = facilityTestDataFactory.createFacility("Internet", "WiFi is available in the hotel rooms and is free of charge.");

        EditHotelRequest goodRequest = new EditHotelRequest(
                "https://www.bla-bla-bla.com/hotel/in/test-mock.html",
                "Test mock Beach Huts",
                "Lorem ipsum",
                List.of(facilityView.id()),
                List.of("Don't smoke"),
                "Agonda Beach,, 403702 Agonda, India",
                "15.04399495",
                "73.98712903",
                "Bed and breakfasts"
        );

        MvcResult createResult = this.mockMvc
                .perform(post("/api/v1/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, goodRequest)))
                .andExpect(status().isOk())
                .andReturn();

        HotelView hotelView = fromJson(objectMapper, createResult.getResponse().getContentAsString(), HotelView.class);
        assertNotNull(hotelView.id(), "Hotel id must not be null!");
        assertEquals(goodRequest.name(), hotelView.name(), "Hotel name update isn't applied!");
    }

    @Test
    public void testCreateFail() throws Exception {
        EditHotelRequest badRequest = new EditHotelRequest();

        this.mockMvc
                .perform(post("/api/v1/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, badRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Method argument validation failed")));
    }

    @Test
    public void testEditSuccess() throws Exception {
        HotelView hotelView = hotelTestDataFactory.createHotel(
                "Test mock Beach Huts",
                "Agonda Beach,, 403702 Agonda, India",
                "Bed and breakfasts"
        );

        EditHotelRequest updateRequest = new EditHotelRequest(
                null,
                "New name for Beach Huts",
                "New description for the hotel",
                null,
                null,
                null,
                null,
                null,
                null
        );

        MvcResult updateResult = this.mockMvc
                .perform(put(String.format("/api/v1/hotels/%s", hotelView.id()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, updateRequest)))
                .andExpect(status().isOk())
                .andReturn();
        HotelView newHotelView = fromJson(objectMapper, updateResult.getResponse().getContentAsString(), HotelView.class);

        assertEquals(updateRequest.name(), newHotelView.name(), "Hotel name update isn't applied!");
        assertEquals(updateRequest.description(), newHotelView.description(), "Hotel description update isn't applied!");
    }

    @Test
    public void testEditFailBadRequest() throws Exception {
        HotelView hotelView = hotelTestDataFactory.createHotel(
                "Test mock Beach Huts",
                "Agonda Beach,, 403702 Agonda, India",
                "Bed and breakfasts"
        );

        EditHotelRequest updateRequest = new EditHotelRequest();

        this.mockMvc
                .perform(put(String.format("/api/v1/hotels/%s", hotelView.id()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Method argument validation failed")));
    }

    @Test
    public void testEditFailNotFound() throws Exception {
        EditHotelRequest updateRequest = new EditHotelRequest(
                null,
                "New name for Beach Huts",
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        this.mockMvc
                .perform(put(String.format("/api/v1/hotels/%s", "5f07c259ffb98843e36a2aa9"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, updateRequest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity Hotel with id 5f07c259ffb98843e36a2aa9 not found")));
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        HotelView hotelView = hotelTestDataFactory.createHotel(
                "Test mock Beach Huts",
                "Agonda Beach,, 403702 Agonda, India",
                "Bed and breakfasts"
        );
        this.mockMvc
                .perform(delete(String.format("/api/v1/hotels/%s", hotelView.id())))
                .andExpect(status().isOk());

        this.mockMvc
                .perform(get(String.format("/api/v1/hotels/%s", hotelView.id())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteFailNotFound() throws Exception {
        this.mockMvc
                .perform(delete(String.format("/api/v1/hotels/%s", "5f07c259ffb98843e36a2aa9")))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity Hotel with id 5f07c259ffb98843e36a2aa9 not found")));
    }

    @Test
    public void testGetSuccess() throws Exception {
        HotelView hotelView = hotelTestDataFactory.createHotel(
                "Test mock Beach Huts",
                "Agonda Beach,, 403702 Agonda, India",
                "Bed and breakfasts"
        );

        MvcResult getResult = this.mockMvc
                .perform(get(String.format("/api/v1/hotels/%s", hotelView.id())))
                .andExpect(status().isOk())
                .andReturn();

        HotelView getHotelView = fromJson(objectMapper, getResult.getResponse().getContentAsString(), HotelView.class);

        assertEquals(hotelView.id(), getHotelView.id(), "Hotel ids must be equal!");
    }

    @Test
    public void testGetNotFound() throws Exception {
        this.mockMvc
                .perform(get(String.format("/api/v1/hotels/%s", "5f07c259ffb98843e36a2aa9")))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity Hotel with id 5f07c259ffb98843e36a2aa9 not found")));
    }

    @Test
    public void testGetHotelFacilities() throws Exception {
        FacilityView facilityView1 = facilityTestDataFactory.createFacility("Internet", "WiFi is available in the hotel rooms and is free of charge.");
        FacilityView facilityView2 = facilityTestDataFactory.createFacility("Parking", "No parking available.");

        HotelView hotelView = hotelTestDataFactory.createHotel(
                "Test mock Beach Huts",
                "Agonda Beach,, 403702 Agonda, India",
                List.of(facilityView1.id(), facilityView2.id()),
                "Bed and breakfasts"
        );

        MvcResult getFacilitiesResult = this.mockMvc
                .perform(get(String.format("/api/v1/hotels/%s/facilities", hotelView.id())))
                .andExpect(status().isOk())
                .andReturn();

        ListResponse<FacilityView> facilityViewList = fromJson(objectMapper,
                getFacilitiesResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertEquals(2, facilityViewList.items().size(), "Hotel must have 2 facilities");
        assertEquals(facilityView1.name(), facilityViewList.items().get(0).name(), "Facility name mismatch!");
        assertEquals(facilityView2.name(), facilityViewList.items().get(1).name(), "Facility name mismatch!");

    }
}
