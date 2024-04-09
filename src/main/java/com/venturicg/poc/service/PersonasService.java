package com.venturicg.poc.service;

import com.venturicg.poc.service.model.Persona;
import com.venturicg.poc.web.dto.PersonaDTO;

import java.util.List;
import java.util.Optional;

public interface PersonasService {

    Optional<Persona> registrar(PersonaDTO datos);

    Optional<Persona> editar(PersonaDTO datos);

    Boolean eliminar(Long personaId);

    List<Persona> listar();

    Persona buscar(Long personaId);

    Persona buscar(PersonaDTO datos);

}
