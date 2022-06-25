package com.github.alexeyzausalin.reservation.api;

import com.github.alexeyzausalin.reservation.domain.dto.*;
import com.github.alexeyzausalin.reservation.service.FacilityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Facility")
@RestController
@RequestMapping("/api/v1/facilities")
@RequiredArgsConstructor
public class FacilityApi {

    private final FacilityService facilityService;
    
    @PostMapping
    public FacilityView create(@RequestBody @Valid EditFacilityRequest request) {
        return facilityService.create(request);
    }

    @PutMapping("{id}")
    public FacilityView edit(@PathVariable String id, @RequestBody @Valid EditFacilityRequest request) {
        return facilityService.update(new ObjectId(id), request);
    }

    @DeleteMapping("{id}")
    public FacilityView delete(@PathVariable String id) {
        return facilityService.delete(new ObjectId(id));
    }

    @GetMapping("{id}")
    public FacilityView get(@PathVariable String id) {
        return facilityService.getFacility(new ObjectId(id));
    }

    @PostMapping("search")
    public ListResponse<FacilityView> search(@RequestBody @Valid SearchRequest<SearchFacilitiesQuery> request) {
        return new ListResponse<>(facilityService.searchFacilities(request.page(), request.query()));
    }
}
