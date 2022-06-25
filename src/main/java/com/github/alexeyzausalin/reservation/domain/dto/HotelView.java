package com.github.alexeyzausalin.reservation.domain.dto;

import java.util.List;

public record HotelView(

        String id,

        String pageUrl,

        String name,
        String description,

        List<String> rules,

        String address,

        String latitude,
        String longitude,

        String hotelType
) {
}
