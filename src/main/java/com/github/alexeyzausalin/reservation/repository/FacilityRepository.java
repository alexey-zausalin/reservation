package com.github.alexeyzausalin.reservation.repository;

import com.github.alexeyzausalin.reservation.entity.Facility;
import com.github.alexeyzausalin.reservation.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    default Facility getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(Facility.class, id));
    }
}
