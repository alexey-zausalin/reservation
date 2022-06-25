package com.github.alexeyzausalin.reservation.api;

import com.github.alexeyzausalin.reservation.domain.dto.*;
import com.github.alexeyzausalin.reservation.service.FacilityService;
import com.github.alexeyzausalin.reservation.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/hotels")
@RequiredArgsConstructor
public class HotelApi {

    private final HotelService hotelService;

    private final FacilityService facilityService;

    @PostMapping
    public HotelView create(@RequestBody @Valid EditHotelRequest request) {
        return hotelService.create(request);
    }

    @PutMapping("{id}")
    public HotelView edit(@PathVariable String id, @RequestBody @Valid EditHotelRequest request) {
        return hotelService.update(new ObjectId(id), request);
    }

    @DeleteMapping("{id}")
    public HotelView delete(@PathVariable String id) {
        return hotelService.delete(new ObjectId(id));
    }

    @GetMapping("{id}")
    public HotelView get(@PathVariable String id) {
        return hotelService.getHotel(new ObjectId(id));
    }

    @GetMapping("{id}/facilities")
    public ListResponse<FacilityView> getFacilities(@PathVariable String id) {
        return new ListResponse<>(facilityService.getHotelFacilities(new ObjectId(id)));
    }

    @PostMapping("search")
    public ListResponse<HotelView> search(@RequestBody @Valid SearchRequest<SearchHotelsQuery> request) {
        return new ListResponse<>(hotelService.searchHotels(request.page(), request.query()));
    }
}
