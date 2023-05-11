package com.airportsmanagementapp.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Airport {
    @Id
    private String code;
    private String name;
    private String country;
    private Long annualNumberOfPassengers;

    public Airport() {

    }

    public Airport(String code, String name, String country, Long annualNumberOfPassengers) {
        this.code = code;
        this.name = name;
        this.country = country;
        this.annualNumberOfPassengers = annualNumberOfPassengers;
    }

}
