package com.github.alexeyzausalin.reservation.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class HotelApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenNewHotel_whenCreateHotel_shouldCreateNewHotel() throws Exception {
        this.mockMvc
                .perform(post("/api/v1/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Hotel name\",\"description\":\"Description of the hotel\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty());
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
    public void givenExistedHotelId_whenDeleteHotel_shouldDeleteHotel() throws Exception {
        this.mockMvc
                .perform(delete("/api/v1/hotels/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNotExistedHotelId_whenDeleteHotel_shouldReturnNotFoundError() throws Exception {
        this.mockMvc
                .perform(delete("/api/v1/hotels/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenExistedHotelId_whenGetHotel_shouldReturnHotel() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/hotels/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1));
    }

    @Test
    public void givenNotExistedHotelId_whenGetHotel_shouldReturnNotFoundError() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/hotels/0"))
                .andExpect(status().isNotFound());
    }
}
