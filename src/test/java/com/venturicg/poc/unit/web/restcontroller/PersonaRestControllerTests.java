package com.venturicg.poc.unit.web.restcontroller;

import com.venturicg.poc.service.PaisesService;
import com.venturicg.poc.service.PersonasService;
import com.venturicg.poc.service.model.Pais;
import com.venturicg.poc.service.model.Persona;
import com.venturicg.poc.web.dto.PaisDTO;
import com.venturicg.poc.web.dto.PersonaDTO;
import com.venturicg.poc.web.restcontroller.PersonaRestController;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PersonaRestControllerTests {

    @Mock
    PersonasService personasService;

    @Mock
    PaisesService paisesService;

    @Test
    void cuandoUsaDatosValidos_EntoncesCreaPersonaCorrectamente() {
        PaisDTO paisDTO = new PaisDTO("NJ", "Nunca Jamás");
        PersonaDTO personaDTO = new PersonaDTO("Sutano", "Perenciano", paisDTO);

        Pais pais = new Pais();
        pais.setId(paisDTO.getId());
        pais.setNombre(paisDTO.getNombre());
        Persona persona = new Persona(personaDTO.getNombre(), personaDTO.getApellido(), paisDTO.getId());
        persona.setId(1L);

        when(personasService.registrar(any())).thenReturn(Optional.of(persona));
        when(paisesService.buscar(any())).thenReturn(pais);
        
        PersonaRestController personaRestControllerMock = new PersonaRestController(personasService, paisesService);

        ResponseEntity<PersonaDTO> response = personaRestControllerMock.registrarPersona(personaDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void cuandoUsaDatosValidos_EntoncesEditaPersonaCorrectamente() {
        PaisDTO paisDTO = new PaisDTO("NJ", "Nunca Jamás");
        PersonaDTO personaDTO = new PersonaDTO("Sutano", "Perenciano", paisDTO);

        Pais pais = new Pais();
        pais.setId(paisDTO.getId());
        pais.setNombre(paisDTO.getNombre());
        Persona persona = new Persona(personaDTO.getNombre(), personaDTO.getApellido(), paisDTO.getId());
        persona.setId(1L);

        when(personasService.editar(any())).thenReturn(Optional.of(persona));
        when(paisesService.buscar(any())).thenReturn(pais);

        PersonaRestController personaRestControllerMock = new PersonaRestController(personasService, paisesService);

        ResponseEntity<PersonaDTO> response = personaRestControllerMock.editarPersona(1L, personaDTO);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    void cuandoUsaDatosValidos_EntoncesListaPersonasCorrectamente() {
        PaisDTO paisDTO = new PaisDTO("NJ", "Nunca Jamás");
        PersonaDTO personaDTO = new PersonaDTO("Sutano", "Perenciano", paisDTO);

        Pais pais = new Pais();
        pais.setId(paisDTO.getId());
        pais.setNombre(paisDTO.getNombre());
        Persona persona = new Persona(personaDTO.getNombre(), personaDTO.getApellido(), paisDTO.getId());
        persona.setId(1L);

        when(personasService.listar()).thenReturn(Collections.singletonList(persona));
        when(paisesService.buscar(any())).thenReturn(pais);

        PersonaRestController personaRestControllerMock = new PersonaRestController(personasService, paisesService);

        ResponseEntity<List<PersonaDTO>> response = personaRestControllerMock.listarPersonas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void cuandoUsaDatosValidos_EntoncesEliminaPersonaCorrectamente() {

        when(personasService.eliminar(any())).thenReturn(Boolean.TRUE);

        PersonaRestController personaRestControllerMock = new PersonaRestController(personasService, paisesService);

        ResponseEntity<?> response = personaRestControllerMock.eliminarPersona(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
