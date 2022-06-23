package com.github.alexeyzausalin.reservation.domain.dto;

import java.util.Set;

public record HotelView(

        String id,

        String pageUrl,

        String name,
        String description,

        Set<String> facilities,
        Set<String> rules,

        String address,

        String latitude,
        String longitude,

        String hotelType
) {
}
