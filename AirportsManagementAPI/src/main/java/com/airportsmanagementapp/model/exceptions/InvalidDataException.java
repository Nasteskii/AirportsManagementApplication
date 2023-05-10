package com.airportsmanagementapp.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException() {
        super("Invalid data exception");
    }
}
