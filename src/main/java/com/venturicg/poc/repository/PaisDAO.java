package com.venturicg.poc.repository;

import com.venturicg.poc.service.model.Pais;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PaisDAO {

    @Select("select * from pais")
    List<Pais> obtenerPaises();

    @Select("SELECT * FROM pais WHERE id = #{id}")
    Optional<Pais> buscar(String id);
}
