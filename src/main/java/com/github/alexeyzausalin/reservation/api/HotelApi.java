package com.github.alexeyzausalin.reservation.api;

import com.github.alexeyzausalin.reservation.domain.dto.HotelView;
import com.github.alexeyzausalin.reservation.domain.dto.ListResponse;
import com.github.alexeyzausalin.reservation.domain.dto.SearchHotelsQuery;
import com.github.alexeyzausalin.reservation.domain.dto.SearchRequest;
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

    @GetMapping("{id}")
    public HotelView get(@PathVariable String id) {
        return hotelService.getHotel(new ObjectId(id));
    }

    @PostMapping("search")
    public ListResponse<HotelView> search(@RequestBody @Valid SearchRequest<SearchHotelsQuery> request) {
        return new ListResponse<HotelView>(hotelService.searchHotels(request.page(), request.query()));
    }
}
