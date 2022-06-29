package com.github.alexeyzausalin.reservation.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class HelloApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void sayHello_shouldReturnGreetingMessage() throws Exception {
        this.mockMvc
                .perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Hello"));
    }
}
