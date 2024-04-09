package com.venturicg.poc.service.impl;

import com.venturicg.poc.repository.PaisDAO;
import com.venturicg.poc.service.PaisesService;
import com.venturicg.poc.service.exception.PaisOperacionException;
import com.venturicg.poc.service.model.Pais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PaisServiceImpl implements PaisesService {

    @Autowired
    private PaisDAO paisDAO;


    @Override
    public List<Pais> obtenerPaises() {
        return paisDAO.obtenerPaises();
    }

    @Override
    public Pais buscar(String id) {
        Objects.requireNonNull(id, "El id no puede ser nulo o vacio.");
        return paisDAO.buscar(id)
                .orElseThrow(() -> new PaisOperacionException("No se encontró el país con el id " + id, HttpStatus.NOT_FOUND));
    }
}
