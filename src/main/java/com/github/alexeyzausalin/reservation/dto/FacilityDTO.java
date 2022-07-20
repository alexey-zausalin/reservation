package com.github.alexeyzausalin.reservation.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

public record FacilityDTO(

        Long id,

        @NotBlank
        String group,

        @NotBlank
        String name) {

    @Builder
    public FacilityDTO {

    }
}
