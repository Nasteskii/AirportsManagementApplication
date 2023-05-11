package com.airportsmanagementapp.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class NotExistingFlightException extends RuntimeException{
    public NotExistingFlightException(Long id) {
        super(String.format("Flight with id: %d was not found", id));
    }
}
