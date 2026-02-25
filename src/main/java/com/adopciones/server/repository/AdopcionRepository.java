package com.adopciones.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adopciones.server.models.Adopcion;

public interface AdopcionRepository  extends JpaRepository<Adopcion, Integer>{
    
}
