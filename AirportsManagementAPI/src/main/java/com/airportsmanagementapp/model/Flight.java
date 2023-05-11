package com.airportsmanagementapp.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Airport from;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Airport to;
    private int departureTimeInMinutesSinceMidnight;
    private int durationInMinutes;

    public Flight() {}

    public Flight(Airport from, Airport to, int departureTimeInMinutesSinceMidnight, int durationInMinutes) {
        this.from = from;
        this.to = to;
        this.departureTimeInMinutesSinceMidnight = departureTimeInMinutesSinceMidnight;
        this.durationInMinutes = durationInMinutes;
    }

}
