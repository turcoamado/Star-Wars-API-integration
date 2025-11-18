package com.conexa.challenge.starwars.application.exception;

public class ExternalServiceTimeoutException extends RuntimeException {
    public ExternalServiceTimeoutException(String message) {
        super(message);
    }
}
