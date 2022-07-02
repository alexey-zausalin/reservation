package com.github.alexeyzausalin.reservation.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public record HotelDTO(
        Long id,

        @NotBlank
        String name,

        @NotNull
        String description,

        List<@NotNull Long> facilityIds) {

    @Builder
    public HotelDTO {

    }
}
