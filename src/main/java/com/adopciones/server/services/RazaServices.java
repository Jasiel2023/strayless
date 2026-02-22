package com.adopciones.server.services;

import java.util.List;

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
        if (raza.getEspecie() == null || raza.getEspecie().getId() == null) {
            throw new IllegalArgumentException("La especie no puede ser nula o tener ID nulo");
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

     public List<Raza> getAllRazas() {
        return razaRepository.findAll();
    }

}
