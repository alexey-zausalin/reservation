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
public class ExceptionHandlerTest {

    private final MockMvc mockMvc;

    @Autowired
    public ExceptionHandlerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void givenNotExistedHotelId_whenGetHotel_shouldReturnNotFoundError() throws Exception {
        mockMvc.perform(get("/api/v1/hotels/0"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Not found exception"));
    }

    @Test
    public void givenInvalidNewHotel_whenCreateHotel_shouldReturnBadRequestError() throws Exception {
        mockMvc.perform(post("/api/v1/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"description\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Method argument validation failed"));
    }

    @Test
    public void givenInvalidTypeHotelId_whenGetHotel_shouldReturnBadRequestError() throws Exception {
        mockMvc.perform(get("/api/v1/hotels/not_valid_id"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Method argument type mismatch"));
    }
}
