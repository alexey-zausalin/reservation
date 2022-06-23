package com.github.alexeyzausalin.reservation.domain.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

import java.util.Set;

public record EditHotelRequest(

        String pageUrl,

        @NotBlank String name,
        String description,

        Set<String> facilities,
        Set<String> rules,

        @NotBlank String address,

        String latitude,
        String longitude,

        @NotBlank String hotelType
) {

    @Builder
    public EditHotelRequest {

    }

    public EditHotelRequest() {
        this(null, null, null, null, null, null, null, null, null);
    }
}
