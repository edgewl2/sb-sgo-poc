package com.venturicg.poc.integration.web.restcontroller;

import com.venturicg.poc.web.dto.PaisDTO;
import com.venturicg.poc.web.dto.PersonaDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Rollback
class PersonaRestControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void cuandoUsaDatosValidos_EntoncesCreaPersonaCorrectamente() throws Exception {
        PaisDTO paisDTO = new PaisDTO("AF", "Afghanistan");
        PersonaDTO personaDTO = new PersonaDTO("Sutano", "Perenciano", paisDTO);

        this.mockMvc.perform(post("/rest/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personaDTO.toString()))
                .andExpect(status().isCreated());
    }

    @Test()
    void cuandoUsaDatosValidos_EntoncesEditaPersonaCorrectamente() throws Exception {
        PaisDTO paisDTO = new PaisDTO("AZ", "Azerbaijan");
        PersonaDTO personaDTO = new PersonaDTO("Sutano", "Perenciano", paisDTO);

        this.mockMvc.perform(post("/rest/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personaDTO.toString()))
                .andExpect(status().isCreated());

        paisDTO.setId("AZ");
        paisDTO.setNombre("Azerbaijan");
        personaDTO.setPais(paisDTO);

        this.mockMvc.perform(put("/rest/personas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personaDTO.toString()))
                .andExpect(status().isAccepted());
    }

    @Test
    void cuandoUsaDatosValidos_EntoncesListaPersonasCorrectamente() throws Exception {

        this.mockMvc.perform(get("/rest/personas"))
                .andExpect(status().isOk());
    }

    @Test
    void cuandoUsaDatosValidos_EntoncesEliminaPersonaCorrectamente() throws Exception {
        PaisDTO paisDTO = new PaisDTO("AZ", "Azerbaijan");
        PersonaDTO personaDTO = new PersonaDTO("Fulano", "Perenciano", paisDTO);

        this.mockMvc.perform(post("/rest/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personaDTO.toString()))
                .andExpect(status().isCreated());

        this.mockMvc.perform(delete("/rest/personas/1"))
                .andExpect(status().isNoContent());
    }
}
