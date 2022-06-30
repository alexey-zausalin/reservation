package com.github.alexeyzausalin.reservation.api;

import com.github.alexeyzausalin.reservation.service.HotelService;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelApi {

    private final HotelService hotelService;

    @Autowired
    public HotelApi(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public String create(@RequestBody String newHotel) {
        Object newHotelObj = JSONValue.parse(newHotel);
        JSONObject newHotelJsonObject = (JSONObject) newHotelObj;

        String name = newHotelJsonObject.getAsString("name");
        if (name == null || name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not empty 'name' is required");
        }

        return hotelService.createHotel(newHotel);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody String updateHotel) {
        String hotel = hotelService.getHotel(id);
        if (hotel == null) {
            throw new HotelNotFoundException();
        }

        Object updateHotelObj = JSONValue.parse(updateHotel);
        JSONObject updateHotelJsonObject = (JSONObject) updateHotelObj;

        String description = updateHotelJsonObject.getAsString("description");
        if (description == null || description.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not empty 'description' is required");
        }

        return hotelService.updateHotel(id, updateHotel);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (hotelService.deleteHotel(id) == null) {
            throw new HotelNotFoundException();
        }
    }

    @GetMapping("/{id}")
    public String getHotel(@PathVariable Long id) {
        String hotel = hotelService.getHotel(id);
        if (hotel == null) {
            throw new HotelNotFoundException();
        }

        return hotel;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void hotelNotFoundHandler(HotelNotFoundException exception) {

    }
}
