package com.venturicg.poc.service.impl;

import com.venturicg.poc.repository.PersonaDAO;
import com.venturicg.poc.service.PersonasService;
import com.venturicg.poc.service.exception.PersonaOperacionException;
import com.venturicg.poc.service.model.Persona;
import com.venturicg.poc.service.utils.PersonaUtils;
import com.venturicg.poc.web.dto.PersonaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PersonaServiceImpl implements PersonasService {

    private final PersonaDAO personaDAO;

    @Autowired
    public PersonaServiceImpl(PersonaDAO personaDAO) {
        this.personaDAO = personaDAO;
    }

    @Transactional
    @Override
    public Optional<Persona> registrar(PersonaDTO datos) {

        Optional<Persona> persona = Optional.of(this.buscar(datos));
        boolean personaRegistrada = this.personaDAO.registrar(persona.get());

        if (!personaRegistrada) {
            throw new PersonaOperacionException("La persona no fue registrada.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return persona;
    }

    @Transactional
    @Override
    public Optional<Persona> editar(PersonaDTO datos) {
        Optional<Persona> persona = Optional.of(PersonaUtils.validarPersona(datos))
                .filter(personaDTO -> personaDTO.getId() > 0L)
                .map(personaDTO -> {
                    Persona personaTemp = Objects.requireNonNull(this.buscar(personaDTO.getId()));
                    personaTemp.setNombre(personaDTO.getNombre());
                    personaTemp.setApellido(personaDTO.getApellido());
                    personaTemp.setPaisId(personaDTO.getPais().getId());
                    return personaTemp;
                });
        boolean personaEditada = this.personaDAO.editar(persona.get());

        if (!personaEditada) {
            throw new PersonaOperacionException("La persona no fue editada.", HttpStatus.NOT_ACCEPTABLE);
        }

        return persona;
    }

    @Override
    public Boolean eliminar(Long personaId) {
        Objects.requireNonNull(personaId, "El identificador no debe ser nulo");
        boolean personaEliminada = this.personaDAO.eliminar(personaId);

        if(!personaEliminada) {
            throw new PersonaOperacionException("La persona no fue eliminada.", HttpStatus.EXPECTATION_FAILED);
        }

        return true;
    }

    @Override
    public List<Persona> listar() {
        return this.personaDAO.listar();
    }


    @Override
    public Persona buscar(Long personaId) {
        Objects.requireNonNull(personaId, "El identificador no debe ser nulo");
        Optional<Persona> persona = personaDAO.buscar(personaId);

        if (!persona.isPresent()){
            throw new PersonaOperacionException("La persona no se encuentra registrada.", HttpStatus.NOT_FOUND);
        }

        return persona.get();
    }

    @Override
    public Persona buscar(PersonaDTO datos) {
        Optional<Persona> persona = Optional.of(PersonaUtils.validarPersona(datos))
                .map(PersonaDTO.mapPersonaDTOAPersona(datos.getPais()));

        Optional<Persona> personaExistente = personaDAO.buscarSinId(persona.get());

        if (personaExistente.isPresent()){
            throw new PersonaOperacionException("La persona se encuentra registrada.", HttpStatus.CONFLICT);
        }

        return persona.get();
    }

}
