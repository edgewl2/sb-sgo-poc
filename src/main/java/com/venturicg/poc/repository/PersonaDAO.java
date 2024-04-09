package com.venturicg.poc.repository;

import com.venturicg.poc.service.model.Persona;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
//import org.mybatis.spring.boot.autoconfigure.H2Dialect;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PersonaDAO {

    @Select("SELECT * FROM persona WHERE id = #{id}")
    @Results(id = "personaResultMapWhere", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "nombre", column = "nombre"),
            @Result(property = "apellido", column = "apellido"),
            @Result(property = "paisId", column = "pais_id")
    })
    Optional<Persona> buscar(Long id);

    @Select("SELECT * FROM persona WHERE nombre = #{nombre} and apellido = #{apellido} and pais_id = #{paisId}")
    @Results(id = "personaResultMapWhereMulti", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "nombre", column = "nombre"),
            @Result(property = "apellido", column = "apellido"),
            @Result(property = "paisId", column = "pais_id")
    })
    Optional<Persona> buscarSinId(Persona persona);

    @Select("SELECT * FROM persona")
    @Results(id = "personaResultMapAll", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "nombre", column = "nombre"),
            @Result(property = "apellido", column = "apellido"),
            @Result(property = "paisId", column = "pais_id")
    })
    List<Persona> listar();

    @Insert("INSERT INTO persona (nombre, apellido, pais_id) VALUES (#{nombre}, #{apellido}, #{paisId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Boolean registrar(Persona persona);

    @Update("UPDATE persona SET nombre = #{nombre}, apellido = #{apellido}, pais_id = #{paisId} WHERE id = #{id}")
    Boolean editar(Persona persona);


    @Delete("DELETE FROM persona WHERE id = #{id}")
    Boolean eliminar(Long id);
}
