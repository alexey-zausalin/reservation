package com.github.alexeyzausalin.reservation.repository;

import com.github.alexeyzausalin.reservation.exception.NotFoundException;
import com.github.alexeyzausalin.reservation.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    default Hotel getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(Hotel.class, id));
    }
}
