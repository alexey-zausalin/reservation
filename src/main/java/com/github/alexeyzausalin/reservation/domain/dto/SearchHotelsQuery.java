package com.github.alexeyzausalin.reservation.domain.dto;

import java.time.LocalDateTime;

public record SearchHotelsQuery(
        String id,

        LocalDateTime createdAtStart,
        LocalDateTime createdAtEnd,

        String title
) {
}
