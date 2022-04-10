package com.afkl.travel.exercise.exception;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Exception mapping class for error handling.
 */
@Data
public class ErrorDetails {

    private String message;
    private String errorId;
    private String details;
    private String timestamp;

    public ErrorDetails(String message, String errorId, String details) {
        this.message = message;
        this.errorId = errorId;
        this.details = details;
        this.timestamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
    }
}
