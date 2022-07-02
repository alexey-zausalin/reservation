package com.github.alexeyzausalin.reservation.api;

import com.github.alexeyzausalin.reservation.dto.FacilityDTO;
import com.github.alexeyzausalin.reservation.dto.HotelDTO;
import com.github.alexeyzausalin.reservation.dto.ListResponseDTO;
import com.github.alexeyzausalin.reservation.service.FacilityService;
import com.github.alexeyzausalin.reservation.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelApi {

    private final HotelService hotelService;

    private final FacilityService facilityService;

    @Autowired
    public HotelApi(
            HotelService hotelService,
            FacilityService facilityService) {
        this.hotelService = hotelService;
        this.facilityService = facilityService;
    }

    @PostMapping
    public HotelDTO create(@RequestBody @Valid HotelDTO newHotel) {
        return hotelService.createHotel(newHotel);
    }

    @PutMapping("/{id}")
    public HotelDTO update(@PathVariable Long id, @RequestBody @Valid HotelDTO updateHotel) {
        return hotelService.updateHotel(id, updateHotel);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        hotelService.deleteHotel(id);
    }

    @GetMapping("/{id}")
    public HotelDTO getHotel(@PathVariable Long id) {
        return hotelService.getHotel(id);
    }

    @GetMapping("/{id}/facilities")
    public ListResponseDTO<FacilityDTO> getFacilities(@PathVariable Long id) {
        return new ListResponseDTO<>(facilityService.getHotelFacilities(id));
    }
}
