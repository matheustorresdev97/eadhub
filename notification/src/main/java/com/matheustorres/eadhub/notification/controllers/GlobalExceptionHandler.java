package com.matheustorres.eadhub.notification.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Access Denied: You do not have permission to access this resource.");
        body.put("error", "Forbidden");
        body.put("status", HttpStatus.FORBIDDEN.value());

        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }
}
