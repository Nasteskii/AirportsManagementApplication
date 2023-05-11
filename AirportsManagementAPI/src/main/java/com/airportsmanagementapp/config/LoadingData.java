package com.airportsmanagementapp.config;

import com.airportsmanagementapp.model.Airport;
import com.airportsmanagementapp.model.enumerations.Role;
import com.airportsmanagementapp.service.AirportService;
import com.airportsmanagementapp.service.FlightService;
import com.airportsmanagementapp.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class LoadingData {
    private final AirportService airportService;
    private final FlightService flightService;
    private final UserService userService;

    public LoadingData(AirportService airportService, FlightService flightService, UserService userService) {
        this.airportService = airportService;
        this.flightService = flightService;
        this.userService = userService;
    }

    @PostConstruct
    public void loadAirportsAndFlights() {
        String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/static/airports.csv"));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] airport = line.split(";");
                this.airportService.save(airport[2], airport[0], airport[1], Long.parseLong(airport[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/static/flights.csv"));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] flight = line.split(";");
                if (airportService.findByCode(flight[0]).isPresent() && airportService.findByCode(flight[1]).isPresent()) {
                    Airport from = airportService.findByCode(flight[0]).get();
                    Airport to = airportService.findByCode(flight[1]).get();
                    this.flightService.save(from, to, Integer.parseInt(flight[2]), Integer.parseInt(flight[3]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        userService.register("administrator", "admin", "admin", "A", "B", Role.ROLE_ADMIN);
    }
}
