package com.github.alexeyzausalin.reservation.domain.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

public record EditHotelRequest(

        String pageUrl,

        @NotBlank String name,
        String amenities,

        String city,
        String address,


        String latitude,
        String longitude,

        String hotelType
) {

    @Builder
    public EditHotelRequest {

    }

    public EditHotelRequest() {
        this(null, null, null, null, null, null, null, null);
    }
}
