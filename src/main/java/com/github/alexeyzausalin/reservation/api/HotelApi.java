package com.github.alexeyzausalin.reservation.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelApi {

    @GetMapping("/{id}")
    public String getHotel(@PathVariable Long id) {
        if (id == 0) {
            throw new HotelNotFoundException();
        }

        return String.format("{\"id\":\"%d\"}", id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void hotelNotFoundHandler(HotelNotFoundException exception) {

    }
}
