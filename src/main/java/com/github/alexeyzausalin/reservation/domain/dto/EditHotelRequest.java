package com.github.alexeyzausalin.reservation.domain.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

import java.util.List;

public record EditHotelRequest(

        String pageUrl,

        @NotBlank String name,
        String description,

        List<String> facilities,
        List<String> rules,

        String address,

        String latitude,
        String longitude,

        String hotelType
) {

    @Builder
    public EditHotelRequest {

    }

    public EditHotelRequest() {
        this(null, null, null, null, null, null, null, null, null);
    }
}
