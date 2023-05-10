package com.airportsmanagementapp.repository;

import com.airportsmanagementapp.model.Airport;
import com.airportsmanagementapp.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findAllByFrom(Airport airport);
    List<Flight> findAllByTo(Airport airport);
}
