package com.github.alexeyzausalin.reservation.domain.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public record SearchHotelsQuery(
        String id,

        LocalDateTime createdAtStart,
        LocalDateTime createdAtEnd,

        String name,

        String hotelType
) {
    @Builder
    public SearchHotelsQuery {
    }

    public SearchHotelsQuery() {
        this(null, null, null, null, null);
    }
}
