package com.airportsmanagementapp.web;

import com.airportsmanagementapp.model.Airport;
import com.airportsmanagementapp.model.exceptions.InvalidDataException;
import com.airportsmanagementapp.model.exceptions.NotExistingAirportException;
import com.airportsmanagementapp.service.AirportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/airports")
public class AirportRestController {
    private final AirportService airportService;

    public AirportRestController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public ResponseEntity<List<Airport>> findAll() {
        if (this.airportService.findAll() != null) {
            return ResponseEntity.ok().body(this.airportService.findAll());
        } else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/addAirport")
    public ResponseEntity<Object> save(@RequestParam String code,
                                       @RequestParam(required = false) String newCode,
                                       @RequestParam String name,
                                       @RequestParam String country,
                                       @RequestParam Long annualNumberOfPassengers) {
        if (newCode != null) {
            try {
                this.airportService.save(newCode, code, name, country, annualNumberOfPassengers);
                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (InvalidDataException e) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
            }
        } else {
            try {
                this.airportService.save(code, name, country, annualNumberOfPassengers);
                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (InvalidDataException e) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
            }
        }
    }

    @PostMapping("/delete")
    public ResponseEntity deleteById(@RequestParam String code) {
        try {
            this.airportService.deleteByCode(code);
        } catch (NotExistingAirportException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        if (this.airportService.findByCode(code).isEmpty())
            return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
