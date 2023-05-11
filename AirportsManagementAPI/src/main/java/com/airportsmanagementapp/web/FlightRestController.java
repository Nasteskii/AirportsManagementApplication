package com.airportsmanagementapp.web;

import com.airportsmanagementapp.model.Flight;
import com.airportsmanagementapp.model.exceptions.InvalidDataException;
import com.airportsmanagementapp.model.exceptions.NotExistingFlightException;
import com.airportsmanagementapp.service.AirportService;
import com.airportsmanagementapp.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin("http://localhost:63342")
@RequestMapping("/api/flights")
public class FlightRestController {
    private final FlightService flightService;
    private final AirportService airportService;

    public FlightRestController(FlightService flightService, AirportService airportService) {
        this.flightService = flightService;
        this.airportService = airportService;
    }

    @GetMapping
    public ResponseEntity<List<Flight>> findAll() {
        if (this.flightService.findAll() != null) {
            return ResponseEntity.ok().body(this.flightService.findAll());
        } else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/addFlight")
    public ResponseEntity<Object> save(@RequestParam(required = false) Long id,
                                       @RequestParam String from,
                                       @RequestParam String to,
                                       @RequestParam Integer departureTimeInMinutesSinceMidnight,
                                       @RequestParam Integer durationInMinutes) {
        if (airportService.findByCode(from).isPresent() && airportService.findByCode(to).isPresent()) {
            try {
                if (id != null) {
                    this.flightService.edit(id, airportService.findByCode(from).get(), airportService.findByCode(to).get(), departureTimeInMinutesSinceMidnight, durationInMinutes);
                } else {
                    this.flightService.save(airportService.findByCode(from).get(), airportService.findByCode(to).get(), departureTimeInMinutesSinceMidnight, durationInMinutes);
                }
                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (InvalidDataException e) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/delete")
    public ResponseEntity deleteById(@RequestParam Long id) {
        try {
            this.flightService.deleteById(id);
        } catch (NotExistingFlightException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        if (this.flightService.findById(id).isEmpty())
            return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
