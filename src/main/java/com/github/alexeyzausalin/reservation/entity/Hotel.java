package com.github.alexeyzausalin.reservation.entity;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Hotel {

    private Long id;

    private String name;

    private String description;
}
