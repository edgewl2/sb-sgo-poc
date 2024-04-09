package com.venturicg.poc.service.model;

import java.io.Serializable;

public class Persona implements Serializable {

    private Long id;
    private String nombre;
    private String apellido;
    private String paisId;

    public Persona(String nombre, String apellido, String paisId) {
        this.id = 0L;
        this.nombre = nombre;
        this.apellido = apellido;
        this.paisId= paisId;
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

    public String getPaisId() {
        return paisId;
    }

    public void setPaisId(String paisId) {
        this.paisId = paisId;
    }
}
