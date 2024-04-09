package com.venturicg.poc.service.exception;

import org.springframework.http.HttpStatus;

public class PaisOperacionException extends RuntimeException {
    private HttpStatus status;

    public PaisOperacionException() {
        this.status = HttpStatus.BAD_REQUEST;
    }

    public PaisOperacionException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public PaisOperacionException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
