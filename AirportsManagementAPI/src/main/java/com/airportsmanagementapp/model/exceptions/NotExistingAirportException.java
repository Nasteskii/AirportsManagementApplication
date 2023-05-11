package com.airportsmanagementapp.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class NotExistingAirportException extends RuntimeException{
    public NotExistingAirportException(String code) {
        super(String.format("Airport with code: %d was not found", code));
    }
}
