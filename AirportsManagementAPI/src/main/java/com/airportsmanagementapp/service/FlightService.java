package com.airportsmanagementapp.service;

import com.airportsmanagementapp.model.Airport;
import com.airportsmanagementapp.model.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightService {
    List<Flight> findAll();

    Optional<Flight> findById(Long id);

    Optional<Flight> save(Airport from, Airport to, int departureTimeInMinutesSinceMidnight, int durationInMinutes);

    Optional<Flight> edit(Long id, Airport from, Airport to, int departureTimeInMinutesSinceMidnight, int durationInMinutes);

    void deleteById(Long id);

    void refreshFlights();

}
