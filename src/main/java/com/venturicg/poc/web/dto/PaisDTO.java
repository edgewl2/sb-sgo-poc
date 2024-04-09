package com.venturicg.poc.web.dto;

import java.io.Serializable;

public class PaisDTO implements Serializable {
    private String id;
    private String nombre;

    public PaisDTO() {
        this.id = null;
        this.nombre = null;
    }

    public PaisDTO(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\":\"" + id + "\","
                + "\"nombre\":\"" + nombre + "\""
                + "}";
    }
}
