package com.github.alexeyzausalin.reservation.api;

import com.github.alexeyzausalin.reservation.api.data.FacilityTestDataFactory;
import com.github.alexeyzausalin.reservation.api.data.HotelTestDataFactory;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class HotelApiTest {

    private final MockMvc mockMvc;

    private final HotelTestDataFactory hotelTestDataFactory;

    private final FacilityTestDataFactory facilityTestDataFactory;

    @Autowired
    public HotelApiTest(MockMvc mockMvc,
                        HotelTestDataFactory hotelTestDataFactory,
                        FacilityTestDataFactory facilityTestDataFactory) {
        this.mockMvc = mockMvc;
        this.hotelTestDataFactory = hotelTestDataFactory;
        this.facilityTestDataFactory = facilityTestDataFactory;
    }

    @Test
    public void givenNewHotel_whenCreateHotel_shouldCreateNewHotel() throws Exception {
        MvcResult createHotelResult = mockMvc
                .perform(post("/api/v1/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Hotel name\",\"description\":\"Description of the hotel\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("name").value("Hotel name"))
                .andExpect(jsonPath("description").value("Description of the hotel"))
                .andReturn();

        String hotel = createHotelResult.getResponse().getContentAsString();

        Object obj = JSONValue.parse(hotel);
        JSONObject jsonObject = (JSONObject) obj;

        Long id = Long.parseLong(jsonObject.getAsString("id"));

        mockMvc.perform(get(String.format("/api/v1/hotels/%s", id)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenInvalidNewHotel_whenCreateHotel_shouldReturnBadRequestError() throws Exception {
        mockMvc.perform(post("/api/v1/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"description\":\"Description of the hotel\"}"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/v1/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Description of the hotel\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenExistedHotelIdWithNewHotel_whenUpdateHotel_shouldUpdateHotel() throws Exception {
        String hotel = hotelTestDataFactory.create("Hotel name", "Description of the hotel");

        Long hotelId = getIdJsonJsonString(hotel);

        mockMvc.perform(put(String.format("/api/v1/hotels/%s", hotelId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New hotel name\",\"description\":\"New description of the hotel\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(hotelId))
                .andExpect(jsonPath("name").value("New hotel name"))
                .andExpect(jsonPath("description").value("New description of the hotel"));
    }

    @Test
    public void givenExistedHotelIdWithInvalidHotel_whenUpdateHotel_shouldReturnBadRequestError() throws Exception {
        String hotel = hotelTestDataFactory.create("Hotel name", "Description of the hotel");

        Long hotelId = getIdJsonJsonString(hotel);

        mockMvc.perform(put(String.format("/api/v1/hotels/%s", hotelId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenNotExistedHotelIdWithNewHotel_whenUpdateHotel_shouldReturnNotFoundError() throws Exception {
        mockMvc.perform(put("/api/v1/hotels/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Hotel name\",\"description\":\"\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenExistedHotelId_whenDeleteHotel_shouldDeleteHotel() throws Exception {
        String hotel = hotelTestDataFactory.create("Hotel name", "Description of the hotel");

        Long hotelId = getIdJsonJsonString(hotel);

        mockMvc.perform(delete(String.format("/api/v1/hotels/%s", hotelId)))
                .andExpect(status().isOk());

        mockMvc.perform(get(String.format("/api/v1/hotels/%s", hotelId)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenNotExistedHotelId_whenDeleteHotel_shouldReturnNotFoundError() throws Exception {
        mockMvc.perform(delete("/api/v1/hotels/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenExistedHotelId_whenGetHotel_shouldReturnHotel() throws Exception {
        String hotel = hotelTestDataFactory.create("Hotel name", "Description of the hotel");

        Long hotelId = getIdJsonJsonString(hotel);

        mockMvc.perform(get(String.format("/api/v1/hotels/%s", hotelId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(hotelId))
                .andExpect(jsonPath("name").value("Hotel name"))
                .andExpect(jsonPath("description").value("Description of the hotel"));
    }

    @Test
    public void givenNotExistedHotelId_whenGetHotel_shouldReturnNotFoundError() throws Exception {
        mockMvc.perform(get("/api/v1/hotels/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenHotelId_whenGetFacilities_shouldReturnHotelFacilities() throws Exception {
        String facility = facilityTestDataFactory.create(
                "Parking",
                "Free private parking is possible on site (reservation is not needed).");

        Long facilityId = getIdJsonJsonString(facility);

        String hotel = hotelTestDataFactory.create(
                "Hotel name",
                "Description of the hotel",
                List.of(facilityId));

        Long hotelId = getIdJsonJsonString(hotel);

        mockMvc.perform(get(String.format("/api/v1/hotels/%s/facilities", hotelId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("items.length()").value(1))
                .andExpect(jsonPath("items[0].group").value("Parking"))
                .andExpect(jsonPath("items[0].name").value("Free private parking is possible on site (reservation is not needed)."));
    }

    private Long getIdJsonJsonString(String hotel) {
        Object hotelObj = JSONValue.parse(hotel);
        JSONObject hotelJsonObject = (JSONObject) hotelObj;

        return Long.parseLong(hotelJsonObject.getAsString("id"));
    }
}
