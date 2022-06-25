package com.github.alexeyzausalin.reservation.domain.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;

public record EditFacilityRequest(
        @NotNull String group,
        @NotNull String name) {

    @Builder
    public EditFacilityRequest {

    }

    public EditFacilityRequest() {
        this(null, null);
    }
}
