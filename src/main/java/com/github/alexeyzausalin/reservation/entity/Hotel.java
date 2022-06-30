package com.github.alexeyzausalin.reservation.entity;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Hotel {

    private Long id;

    private String name;

    private String description;

    public String toString() {
        if (id == null) {
            return String.format("{\"name\":\"%s\",\"description\":\"%s\"}", name, description);
        }

        return String.format("{\"id\":\"%d\",\"name\":\"%s\",\"description\":\"%s\"}", id, name, description);
    }
}
