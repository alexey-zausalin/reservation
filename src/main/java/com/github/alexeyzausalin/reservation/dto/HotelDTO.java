package com.github.alexeyzausalin.reservation.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record HotelDTO(
        Long id,

        @NotBlank
        String name,

        @NotNull
        String description) {

    @Builder
    public HotelDTO {

    }
}
