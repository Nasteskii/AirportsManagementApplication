package com.airportsmanagementapp.service;

import com.airportsmanagementapp.model.Airport;

import java.util.List;
import java.util.Optional;

public interface AirportService {
    List<Airport> findAll();

    Optional<Airport> findByCode(String code);

    Optional<Airport> save(String newCode, String code, String name, String country, Long annualNumberOfPassengers);

    Optional<Airport> save(String code, String name, String country, Long annualNumberOfPassengers);

    void deleteByCode(String code);
}
