package com.adopciones.server.services;

import org.springframework.stereotype.Service;

import com.adopciones.server.models.Raza;
import com.adopciones.server.repository.RazaRepository;

import jakarta.transaction.Transactional;

@Service
public class RazaServices {
    
    private final RazaRepository razaRepository;

        RazaServices(RazaRepository razaRepository) {
            this.razaRepository = razaRepository;
        }

    @Transactional
    public Raza createRaza(Raza raza) {
        if (raza.getNombre() == null || raza.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la raza no puede estar vacío");
        }
        return razaRepository.save(raza);
    }

    public Raza updateRaza(Raza raza){

        if (raza.getId() == null ) {
            throw new IllegalArgumentException("El ID de la raza no puede ser nulo");
        }
        return razaRepository.save(raza);
    }

    public void deleteRaza(Integer id){
        if (id == null) {
            throw new IllegalArgumentException("El ID de la raza no puede ser nulo");
        }
        razaRepository.deleteById(id);
    }
}
