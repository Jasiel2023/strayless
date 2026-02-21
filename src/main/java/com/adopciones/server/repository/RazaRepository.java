package com.adopciones.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adopciones.server.models.Raza;

public interface RazaRepository extends JpaRepository<Raza, Integer> {
    
}
