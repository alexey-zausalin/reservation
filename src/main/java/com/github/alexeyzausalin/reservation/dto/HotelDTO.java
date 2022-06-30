package com.github.alexeyzausalin.reservation.dto;


import lombok.Builder;

public record HotelDTO(
        Long id,

        String name,
        String description) {

    @Builder
    public HotelDTO {

    }
}
