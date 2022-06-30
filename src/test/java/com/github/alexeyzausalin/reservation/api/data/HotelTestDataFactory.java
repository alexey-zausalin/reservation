package com.github.alexeyzausalin.reservation.api.data;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class HotelTestDataFactory {

    private final AtomicLong counter = new AtomicLong();

    public String create(String name, String description) {
        return String.format(
                "{\"id\":\"%d\",\"name\":\"%s\",\"description\":\"%s\"}",
                counter.incrementAndGet(), name, description);

    }
}
