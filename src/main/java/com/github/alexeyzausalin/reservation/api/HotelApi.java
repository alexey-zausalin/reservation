package com.github.alexeyzausalin.reservation.api;

import com.github.alexeyzausalin.reservation.dto.HotelDTO;
import com.github.alexeyzausalin.reservation.service.HotelService;
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
    public HotelDTO create(@RequestBody HotelDTO newHotel) {
        if (newHotel.name() == null || newHotel.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not empty 'name' is required");
        }

        return hotelService.createHotel(newHotel);
    }

    @PutMapping("/{id}")
    public HotelDTO update(@PathVariable Long id, @RequestBody HotelDTO updateHotel) {
        if (hotelService.getHotel(id) == null) {
            throw new HotelNotFoundException();
        }

        if (updateHotel.description() == null || updateHotel.description().isBlank()) {
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
    public HotelDTO getHotel(@PathVariable Long id) {
        HotelDTO hotel = hotelService.getHotel(id);
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
