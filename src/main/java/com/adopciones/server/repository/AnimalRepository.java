package com.adopciones.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adopciones.server.models.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    

    List<Animal> findAll();
}
