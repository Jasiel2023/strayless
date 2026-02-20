package com.adopciones.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adopciones.server.models.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    
}
