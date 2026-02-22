package com.adopciones.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.adopciones.server.models.Especie;

public interface EspecieRepository extends JpaRepository<Especie, Integer> {
    List<Especie> findAll();
}
