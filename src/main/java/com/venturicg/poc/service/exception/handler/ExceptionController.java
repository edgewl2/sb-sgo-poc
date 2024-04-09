package com.venturicg.poc.service.exception.handler;

import com.venturicg.poc.service.exception.PaisOperacionException;
import com.venturicg.poc.service.exception.PersonaOperacionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(PersonaOperacionException.class)
    public ResponseEntity<Object> handlePersonaOperacion(PersonaOperacionException e) {
        return enviarRespuesta(e);
    }

    @ExceptionHandler(PaisOperacionException.class)
    public ResponseEntity<Object> handlePaisOperacion(PaisOperacionException e) {
        return enviarRespuesta(e);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleAccessDenied(NullPointerException e) {
        PersonaOperacionException personaOperacionException = new PersonaOperacionException(e.getMessage(), e.getCause(), HttpStatus.BAD_REQUEST);

        return enviarRespuesta(personaOperacionException);
    }

    private ResponseEntity<Object> enviarRespuesta(PersonaOperacionException e) {
        return generarRespuesta(e.getMessage(), e.getStatus(), e.getClass().getSimpleName());
    }

    private ResponseEntity<Object> enviarRespuesta(PaisOperacionException e) {
        return generarRespuesta(e.getMessage(), e.getStatus(), e.getClass().getSimpleName());
    }

    private ResponseEntity<Object> generarRespuesta(String message, HttpStatus status, String simpleName) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("message", message);
        respuesta.put("status", status);
        respuesta.put("type", simpleName);
        respuesta.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));


        return ResponseEntity.status(status)
                .body(respuesta);
    }
}
