package com.venturicg.poc.web.restcontroller;

import com.venturicg.poc.service.PaisesService;
import com.venturicg.poc.service.PersonasService;
import com.venturicg.poc.service.model.Pais;
import com.venturicg.poc.web.dto.PersonaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/personas")
public class PersonaRestController {

    private final PersonasService personasService;

    private final PaisesService paisesService;

    @Autowired
    public PersonaRestController(PersonasService personasService, PaisesService paisesService) {
        this.personasService = personasService;
        this.paisesService = paisesService;
    }

    @GetMapping
    public ResponseEntity<List<PersonaDTO>> listarPersonas() {

        return ResponseEntity.ok(this.personasService.listar().stream()
                .map(persona -> {
                    Pais pais = paisesService.buscar(persona.getPaisId());
                    return PersonaDTO.mapPersonaAPersonaDTO(pais).apply(persona);
                })
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<PersonaDTO> registrarPersona(@RequestBody PersonaDTO datos) {
        Optional<PersonaDTO> personaRegistrada = this.personasService.registrar(datos)
                .map(PersonaDTO.mapPersonaAPersonaDTO(this.paisesService.buscar(datos.getPais().getId())));

        return ResponseEntity.created(URI.create("/rest/personas/" + personaRegistrada.get().getId())).body(personaRegistrada.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPersona(@PathVariable("id") Long id) {
        boolean eliminado = this.personasService.eliminar(id);
        return eliminado? ResponseEntity.noContent().build() : ResponseEntity.internalServerError().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaDTO> editarPersona(@PathVariable("id") Long id, @RequestBody PersonaDTO datos) {
        datos.setId(Objects.requireNonNull(id));

        Optional<PersonaDTO> personaEditada = this.personasService.editar(datos)
                .map(PersonaDTO.mapPersonaAPersonaDTO(this.paisesService.buscar(datos.getPais().getId())));

        return ResponseEntity.accepted().body(personaEditada.get());
    }
}
