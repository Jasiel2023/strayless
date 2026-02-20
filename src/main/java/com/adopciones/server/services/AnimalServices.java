package com.adopciones.server.services;

import java.sql.Date;

import org.springframework.stereotype.Service;

import com.adopciones.server.enums.SaludEnum;
import com.adopciones.server.models.Animal;
import com.adopciones.server.enums.SexoEnum;
import com.adopciones.server.enums.DisponibilidadEnum;
import com.adopciones.server.repository.AnimalRepository;

import jakarta.transaction.Transactional;

@Service
public class AnimalServices {

    private final AnimalRepository animalRepository;

    AnimalServices(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Transactional
    public Animal createAnimal(Animal animal) {

        if (animal.getNombre() == null || animal.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del animal no puede estar vacío");
        }

        if (animal.getFechaLlegada() == null) {
            animal.setFechaLlegada(new Date(System.currentTimeMillis()));
        }

        if (animal.getSexoAnimal() == null || animal.getSaludAnimal() == null) {
            throw new IllegalArgumentException("El sexo y la salud del animal no puede ser nulo");
        }

        if (animal.getDisponibilidad() == null) {
            throw new IllegalArgumentException("La disponibilidad del animal son obligatorios");
        }

        if (animal.getEspecie() == null) {
            throw new IllegalArgumentException("La especie del animal es obligatoria");
        }

        return animalRepository.save(animal);

    }

}
