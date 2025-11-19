package com.conexa.challenge.starwars.infrastructure.exception;

import com.conexa.challenge.starwars.application.exception.EmptyListException;
import com.conexa.challenge.starwars.application.exception.ExternalServiceException;
import com.conexa.challenge.starwars.application.exception.ExternalServiceTimeoutException;
import com.conexa.challenge.starwars.application.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(ex.getMessage()));
    }

    @ExceptionHandler(EmptyListException.class)
    public ResponseEntity<?> handleEmptyList(EmptyListException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(buildError(ex.getMessage()));
    }

    @ExceptionHandler(ExternalServiceTimeoutException.class)
    public ResponseEntity<?> handleExternalTimeout(ExternalServiceTimeoutException ex) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
                .body(buildError(ex.getMessage()));
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<?> handleExternalServiceError(ExternalServiceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(buildError(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError("Unexpected error: " + ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(buildError("Invalid username or password: " + ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(buildError("You don't have privileges for this section: " + ex.getMessage()));
    }

    private Map<String, Object> buildError(String message) {
        return Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "error", message
        );
    }
}
