package com.github.alexeyzausalin.reservation.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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

    @NotBlank
    private String name;
    private String amenities;

    @NotBlank
    private String city;
    @NotBlank
    private String address;

    @NotBlank
    private String latitude;
    @NotBlank
    private String longitude;

    @NotBlank
    private String hotelType;
}
