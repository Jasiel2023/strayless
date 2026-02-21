package com.adopciones.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adopciones.server.models.Especie;

public interface EspecieRepository extends JpaRepository<Especie, Integer> {
    
}
