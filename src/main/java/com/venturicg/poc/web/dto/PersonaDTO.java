package com.venturicg.poc.web.dto;

import com.venturicg.poc.service.model.Pais;
import com.venturicg.poc.service.model.Persona;

import java.io.Serializable;
import java.util.function.Function;

public class PersonaDTO implements Serializable {

    private Long id;
    private String nombre;
    private String apellido;
    private PaisDTO pais;

    public PersonaDTO() {
        this.id = -1L;
        this.nombre = null;
        this.apellido = null;
        this.pais = null;
    }

    public PersonaDTO(String nombre, String apellido, PaisDTO pais) {
        this.id = -1L;
        this.nombre = nombre;
        this.apellido = apellido;
        this.pais = pais;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public PaisDTO getPais() {
        return pais;
    }

    public void setPais(PaisDTO pais) {
        this.pais = pais;
    }

    public static Function<Persona, PersonaDTO> mapPersonaAPersonaDTO(Pais pais) {
        return persona -> {
            PaisDTO paisDTO = new PaisDTO(pais.getId(), pais.getNombre());

            PersonaDTO personaDTO = new PersonaDTO(
                    persona.getNombre(),
                    persona.getApellido(),
                    paisDTO);
            personaDTO.setId(persona.getId());

            return personaDTO;
        };
    }

    public static Function<PersonaDTO, Persona> mapPersonaDTOAPersona(PaisDTO paisDTO) {
        return personaDTO -> {
            Pais pais = new Pais();
            pais.setId(paisDTO.getId());
            pais.setNombre(paisDTO.getNombre());

            Persona persona = new Persona(
                    personaDTO.getNombre(),
                    personaDTO.getApellido(),
                    pais.getId());
            persona.setId(personaDTO.getId());

            return persona;
        };
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\":\"" + id + "\","
                + "\"nombre\":\"" + nombre + "\","
                + "\"apellido\":\"" + apellido + "\","
                + "\"pais\":" + pais.toString()
                + "}";
    }
}
