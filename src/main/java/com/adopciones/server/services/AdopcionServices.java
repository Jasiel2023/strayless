package com.adopciones.server.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.adopciones.server.enums.DisponibilidadEnum;
import com.adopciones.server.models.Adopcion;
import com.adopciones.server.models.Animal;
import com.adopciones.server.repository.AdopcionRepository;
import com.adopciones.server.repository.AnimalRepository;
import com.vaadin.copilot.shaded.checkerframework.checker.units.qual.t;

import jakarta.transaction.Transactional;

@Service
public class AdopcionServices {
    
    private final AdopcionRepository adopcionRepository;
    private final AnimalRepository animalRepository;

    public AdopcionServices(AdopcionRepository adopcionRepository, AnimalRepository animalRepository) {
        this.adopcionRepository = adopcionRepository;
        this.animalRepository = animalRepository;
    }


    @Transactional
    public Adopcion createAdopcion(Adopcion adopcion) {

        if (adopcion.getAnimal() == null || adopcion.getUsuario() == null) {
            throw new IllegalArgumentException("El animal y el usuario son obligatorios para crear una adopción");
        }

        Animal animalBD = animalRepository.findById(adopcion.getAnimal().getId())
        .orElseThrow(() -> new IllegalArgumentException("El animal con ID " + adopcion.getAnimal().getId() + " no existe"));

        if (animalBD.getDisponibilidad() != DisponibilidadEnum.DISPONIBLE) {
            throw new IllegalArgumentException("El animal con ID " + animalBD.getId() + " no está disponible para adopción");
         }

          animalBD.setDisponibilidad(DisponibilidadEnum.ADOPTADO);
        animalRepository.save(animalBD);

        if (adopcion.getFechaAdopcion() == null) {
            adopcion.setFechaAdopcion(LocalDate.now());
        }

        adopcion.setAnimal(animalBD);

        return adopcionRepository.save(adopcion);
     }

       

    

    public Adopcion updateAdopcion(Adopcion adopcion) {
        if (adopcion.getId() == null) {
            throw new IllegalArgumentException("El ID de la adopción no puede ser nulo");
        }
        return adopcionRepository.save(adopcion);
    }

    public void deleteAdopcion(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la adopción no puede ser nulo");
        }
        adopcionRepository.deleteById(id);
    }

}
