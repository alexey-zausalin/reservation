package com.github.alexeyzausalin.reservation.dto;

import java.util.List;

public record ListResponseDTO<T>(
        List<T> items
) {

    public ListResponseDTO {
    }

    public ListResponseDTO() {
        this(List.of());
    }
}
