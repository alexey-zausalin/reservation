package com.github.alexeyzausalin.reservation.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "hotels")
@Getter
@Setter
public class Hotel extends ComparableEntity {

    @Id
    private ObjectId id;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private String pageUrl;

    @Indexed
    private String name;
    private String description;

    private Set<String> facilities = new HashSet<>();
    private Set<String> rules = new HashSet<>();

    private String address;

    private String latitude;
    private String longitude;

    @Indexed
    private String hotelType;
}
