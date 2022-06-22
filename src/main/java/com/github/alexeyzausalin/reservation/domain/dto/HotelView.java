package com.github.alexeyzausalin.reservation.domain.dto;

public record HotelView(

        String id,

        String pageUrl,

        String name,
        String amenities,

        String city,
        String address,

        String latitude,
        String longitude,

        String hotelType
) {
}
