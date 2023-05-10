package com.airportsmanagementapp.service.impl;

import com.airportsmanagementapp.model.Airport;
import com.airportsmanagementapp.model.Flight;
import com.airportsmanagementapp.model.exceptions.InvalidDataException;
import com.airportsmanagementapp.model.exceptions.NotExistingAirportException;
import com.airportsmanagementapp.repository.AirportRepository;
import com.airportsmanagementapp.repository.FlightRepository;
import com.airportsmanagementapp.service.AirportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;

    public AirportServiceImpl(AirportRepository airportRepository, FlightRepository flightRepository) {
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Airport> findAll() {
        return airportRepository.findAll();
    }

    @Override
    public Optional<Airport> findByCode(String code) {
        return this.airportRepository.findById(code);
    }

    @Override
    public Optional<Airport> save(String newCode, String code, String name, String country, Long annualNumberOfPassengers) {
        if (newCode.length() != 3 || name.length() < 1 || country.length() < 1 || annualNumberOfPassengers < 0) {
            throw new InvalidDataException();
        }
        if (airportRepository.findById(code).isPresent()) {
            Airport airport = airportRepository.findById(code).get();
            if (airportRepository.findById(newCode).isPresent()) {
                airport.setName(name);
                airport.setCountry(country);
                airport.setAnnualNumberOfPassengers(annualNumberOfPassengers);
                return Optional.of(airportRepository.save(airport));
            } else {
                Airport newAirport = new Airport();
                newAirport.setCode(newCode);
                newAirport.setName(name);
                newAirport.setCountry(country);
                newAirport.setAnnualNumberOfPassengers(annualNumberOfPassengers);
                airportRepository.save(newAirport);
                for (Flight flight : flightRepository.findAllByFrom(airport)) {
                    flight.setFrom(newAirport);
                }
                for (Flight flight : flightRepository.findAllByTo(airport)) {
                    flight.setTo(newAirport);
                }
                airportRepository.deleteById(code);
                return airportRepository.findById(newCode);
            }
        } else {
            Airport airport = new Airport(code, name, country, annualNumberOfPassengers);
            return Optional.of(airportRepository.save(airport));
        }
    }

    @Override
    public Optional<Airport> save(String code, String name, String country, Long annualNumberOfPassengers) {
        if (code.length() != 3 || name.length() < 1 || country.length() < 1 || annualNumberOfPassengers < 0) {
            throw new InvalidDataException();
        }
        if (airportRepository.findById(code).isPresent()) {
            Airport airport = airportRepository.findById(code).get();
            airport.setName(name);
            airport.setCountry(country);
            airport.setAnnualNumberOfPassengers(annualNumberOfPassengers);
            return Optional.of(airportRepository.save(airport));
        } else {
            Airport airport = new Airport(code, name, country, annualNumberOfPassengers);
            return Optional.of(airportRepository.save(airport));
        }
    }

    @Override
    public void deleteByCode(String code) {
        if (code == null || airportRepository.findById(code).isEmpty()) {
            throw new NotExistingAirportException(code);
        }
        this.airportRepository.deleteById(code);
    }
}
