package com.adopciones.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adopciones.server.models.Raza;

public interface RazaRepository extends JpaRepository<Raza, Integer> {
    List<Raza> findAll();
}
