package com.airportsmanagementapp.jobs;

import com.airportsmanagementapp.service.FlightService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private final FlightService flightService;

    public ScheduledTasks(FlightService flightService) {
        this.flightService = flightService;
    }

    @Scheduled(cron = "0 0 1 * * ?" )
    public void refreshFlights() {
        this.flightService.refreshFlights();
    }
}
