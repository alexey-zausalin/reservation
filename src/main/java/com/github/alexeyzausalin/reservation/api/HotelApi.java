package com.github.alexeyzausalin.reservation.api;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelApi {

    @PostMapping
    public String create(@RequestBody String newHotel) {
        Object obj = JSONValue.parse(newHotel);
        JSONObject jsonObject = (JSONObject) obj;

        String name = (String) jsonObject.getAsString("name");
        if (name == null || name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not empty 'name' is required");
        }

        Long id = 1L;
        return String.format("{\"id\":\"%d\"}", id);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody String newHotel) {
        if (id == 0) {
            throw new HotelNotFoundException();
        }

        Object obj = JSONValue.parse(newHotel);
        JSONObject jsonObject = (JSONObject) obj;

        String description = (String) jsonObject.getAsString("description");
        if (description == null || description.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not empty 'description' is required");
        }

        return String.format("{\"id\":\"%d\",\"description\":%s}", id, description);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (id == 0) {
            throw new HotelNotFoundException();
        }
    }

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
