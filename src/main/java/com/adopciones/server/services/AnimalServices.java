package com.adopciones.server.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adopciones.server.models.Animal;
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
            animal.setFechaLlegada(LocalDate.now());
        }

        if (animal.getSexoAnimal() == null || animal.getSaludAnimal() == null) {
            throw new IllegalArgumentException("El sexo y la salud del animal no puede ser nulo");
        }

        if (animal.getDisponibilidad() == null) {
            throw new IllegalArgumentException("La disponibilidad del animal son obligatorios");
        }

        if (animal.getRaza() == null) {
            throw new IllegalArgumentException("La raza del animal no puede ser nula");
            
        }

        return animalRepository.save(animal);
    }

    public Animal updateAnimal(Animal animal){

        if (animal.getId() == null ) {
            throw new IllegalArgumentException("El ID del animal no puede ser nulo");
        }
        return animalRepository.save(animal);
    }

    public void deleteAnimal(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del animal no puede ser nulo");
        }
        animalRepository.deleteById(id);
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

}
