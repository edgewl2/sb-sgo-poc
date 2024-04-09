package com.venturicg.poc.service.utils;

import com.venturicg.poc.web.dto.PersonaDTO;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public final class PersonaUtils {
    private PersonaUtils() {
        throw new UnsupportedOperationException("Esta clase utilitaria no puede ser instanciada");
    }

    public static PersonaDTO validarPersona(PersonaDTO datos) {
        Objects.requireNonNull(datos, "La solicitud es nula.");

        return Optional.of(datos)
                .filter(personaDTO -> Stream.of(personaDTO.getNombre(),
                                personaDTO.getApellido(),
                                personaDTO.getPais(),
                                personaDTO.getPais().getId())
                        .noneMatch(Objects::isNull))
                .filter(personaDTO -> Stream.of(personaDTO.getNombre(),
                                personaDTO.getApellido(),
                                personaDTO.getPais().getId())
                        .noneMatch(String::isEmpty))
                .orElseThrow(() -> new NullPointerException("Campos obligatorios no pueden estar vacíos."));
    }
}
