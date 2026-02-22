package com.adopciones.server.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.adopciones.server.models.Especie;
import com.adopciones.server.repository.EspecieRepository;

import jakarta.transaction.Transactional;

@Service
public class EspecieServices {
    
    private final EspecieRepository especieRepository;
    
    EspecieServices(EspecieRepository especieRepository) {
        this.especieRepository = especieRepository;
    }

    @Transactional
    public Especie createEspecie(Especie especie) {

        if (especie.getNombre() == null || especie.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la especie no puede estar vacío");
        }

        return especieRepository.save(especie);
    }

    public Especie updateEspecie(Especie especie){

        if (especie.getId() == null ) {
            throw new IllegalArgumentException("El ID de la especie no puede ser nulo");
        }
        return especieRepository.save(especie);
    }

    public void deleteEspecie(Integer id){
        if (id == null) {
            throw new IllegalArgumentException("El ID de la especie no puede ser nulo");
        }
        especieRepository.deleteById(id);
    }
    
    public List<Especie> getAllEspecies() {
        return especieRepository.findAll();
    }

    
    


}
