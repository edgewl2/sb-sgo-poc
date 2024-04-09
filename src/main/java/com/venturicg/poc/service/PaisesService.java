package com.venturicg.poc.service;


import com.venturicg.poc.service.model.Pais;

import java.util.List;

public interface PaisesService {

    List<Pais> obtenerPaises();

    Pais buscar(String id);

}
