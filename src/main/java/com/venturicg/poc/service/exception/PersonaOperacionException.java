package com.venturicg.poc.service.exception;

import org.springframework.http.HttpStatus;

public class PersonaOperacionException extends RuntimeException {

    private HttpStatus status;

    public PersonaOperacionException() {
        this.status = HttpStatus.BAD_REQUEST;
    }

    public PersonaOperacionException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public PersonaOperacionException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
