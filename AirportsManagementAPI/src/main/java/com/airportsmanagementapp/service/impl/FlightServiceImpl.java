package com.airportsmanagementapp.service.impl;

import com.airportsmanagementapp.model.Airport;
import com.airportsmanagementapp.model.Flight;
import com.airportsmanagementapp.model.exceptions.InvalidDataException;
import com.airportsmanagementapp.model.exceptions.NotExistingFlightException;
import com.airportsmanagementapp.repository.FlightRepository;
import com.airportsmanagementapp.service.FlightService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Flight> findAll() {
        return this.flightRepository.findAll();
    }

    @Override
    public Optional<Flight> findById(Long id) {
        return flightRepository.findById(id);
    }

    @Override
    public Optional<Flight> save(Airport from, Airport to, int departureTimeInMinutesSinceMidnight, int durationInMinutes) {
        if (departureTimeInMinutesSinceMidnight < 0 || durationInMinutes < 0){
            throw new InvalidDataException();
        }
        Flight flight = new Flight(from, to, departureTimeInMinutesSinceMidnight, durationInMinutes);
        return Optional.of(flightRepository.save(flight));
    }

    @Override
    public Optional<Flight> edit(Long id, Airport from, Airport to, int departureTimeInMinutesSinceMidnight, int durationInMinutes) {
        if (departureTimeInMinutesSinceMidnight < 0 || durationInMinutes < 1){
            throw new InvalidDataException();
        }
        if (flightRepository.findById(id).isPresent()) {
            Flight flight = flightRepository.findById(id).get();
            flight.setFrom(from);
            flight.setTo(to);
            flight.setDepartureTimeInMinutesSinceMidnight(departureTimeInMinutesSinceMidnight);
            flight.setDurationInMinutes(durationInMinutes);
            flightRepository.save(flight);
        } else {
            throw new NotExistingFlightException(id);
        }
        return flightRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null || flightRepository.findById(id).isEmpty()){
            throw new NotExistingFlightException(id);
        }
        this.flightRepository.deleteById(id);
    }

    @Override
    public void refreshFlights() {
        this.flightRepository.deleteAll(flightRepository.findAll().stream().filter(flight -> flight.getDurationInMinutes() > 600).toList());
    }

}
