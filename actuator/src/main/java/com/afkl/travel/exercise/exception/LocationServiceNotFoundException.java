package com.afkl.travel.exercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class LocationServiceNotFoundException extends RuntimeException {

    public LocationServiceNotFoundException(String message) {
        super(message);
    }
}
