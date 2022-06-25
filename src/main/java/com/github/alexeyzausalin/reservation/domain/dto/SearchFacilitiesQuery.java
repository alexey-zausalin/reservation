package com.github.alexeyzausalin.reservation.domain.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public record SearchFacilitiesQuery(
        String id,

        LocalDateTime createdAtStart,
        LocalDateTime createdAtEnd,

        String group,
        String name) {

    @Builder
    public SearchFacilitiesQuery {

    }

    public SearchFacilitiesQuery() {
        this(null, null, null, null, null);
    }
}
