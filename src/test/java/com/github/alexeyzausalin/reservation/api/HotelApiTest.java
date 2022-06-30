package com.github.alexeyzausalin.reservation.api;

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

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class HotelApiTest {

    private final MockMvc mockMvc;

    private final HotelTestDataFactory hotelTestDataFactory;

    @Autowired
    public HotelApiTest(MockMvc mockMvc,
                        HotelTestDataFactory hotelTestDataFactory) {
        this.mockMvc = mockMvc;
        this.hotelTestDataFactory = hotelTestDataFactory;
    }

    @Test
    public void givenNewHotel_whenCreateHotel_shouldCreateNewHotel() throws Exception {
        MvcResult createHotelResult = this.mockMvc
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

        Long id = Long.getLong(jsonObject.getAsString("id"));

        this.mockMvc
                .perform(get(String.format("/api/v1/hotels/%s", id)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenInvalidNewHotel_whenCreateHotel_shouldReturnBadRequestError() throws Exception {
        this.mockMvc
                .perform(post("/api/v1/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"description\":\"Description of the hotel\"}"))
                .andExpect(status().isBadRequest());

        this.mockMvc
                .perform(post("/api/v1/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Description of the hotel\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenExistedHotelIdWithNewHotel_whenUpdateHotel_shouldUpdateHotel() throws Exception {
        String hotel = this.hotelTestDataFactory.create("Hotel name", "Description of the hotel");

        Object obj = JSONValue.parse(hotel);
        JSONObject jsonObject = (JSONObject) obj;

        Long id = Long.getLong(jsonObject.getAsString("id"));

        this.mockMvc
                .perform(put(String.format("/api/v1/hotels/%s", id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"New description of the hotel\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("description").value("New description of the hotel"));
    }

    @Test
    public void givenExistedHotelIdWithInvalidHotel_whenUpdateHotel_shouldReturnBadRequestError() throws Exception {
        String hotel = this.hotelTestDataFactory.create("Hotel name", "Description of the hotel");

        Object obj = JSONValue.parse(hotel);
        JSONObject jsonObject = (JSONObject) obj;

        Long id = Long.getLong(jsonObject.getAsString("id"));

        this.mockMvc
                .perform(put(String.format("/api/v1/hotels/%s", id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenNotExistedHotelIdWithNewHotel_whenUpdateHotel_shouldReturnNotFoundError() throws Exception {
        this.mockMvc
                .perform(put("/api/v1/hotels/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"New description of the hotel\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenExistedHotelId_whenDeleteHotel_shouldDeleteHotel() throws Exception {
        String hotel = this.hotelTestDataFactory.create("Hotel name", "Description of the hotel");

        Object obj = JSONValue.parse(hotel);
        JSONObject jsonObject = (JSONObject) obj;

        Long id = Long.getLong(jsonObject.getAsString("id"));

        this.mockMvc
                .perform(delete(String.format("/api/v1/hotels/%s", id)))
                .andExpect(status().isOk());

        this.mockMvc
                .perform(get(String.format("/api/v1/hotels/%s", id)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenNotExistedHotelId_whenDeleteHotel_shouldReturnNotFoundError() throws Exception {
        this.mockMvc
                .perform(delete("/api/v1/hotels/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenExistedHotelId_whenGetHotel_shouldReturnHotel() throws Exception {
        String hotel = this.hotelTestDataFactory.create("Hotel name", "Description of the hotel");

        Object obj = JSONValue.parse(hotel);
        JSONObject jsonObject = (JSONObject) obj;

        Long id = Long.getLong(jsonObject.getAsString("id"));

        this.mockMvc
                .perform(get(String.format("/api/v1/hotels/%s", id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("name").value("Hotel name"))
                .andExpect(jsonPath("description").value("Description of the hotel"));
    }

    @Test
    public void givenNotExistedHotelId_whenGetHotel_shouldReturnNotFoundError() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/hotels/0"))
                .andExpect(status().isNotFound());
    }
}
