package com.adopciones.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adopciones.server.models.Seguimiento;

public interface SeguimientoRepository  extends JpaRepository<Seguimiento, Integer> {
    
}
