package com.adopciones.server.services;

import com.adopciones.server.models.Seguimiento;
import com.adopciones.server.repository.SeguimientoRepository;

import jakarta.transaction.Transactional;

public class SeguimientoServices {
    
    private final SeguimientoRepository seguimientoRepository;
    
    SeguimientoServices(SeguimientoRepository seguimientoRepository) {
        this.seguimientoRepository = seguimientoRepository;
    }

    @Transactional
    public Seguimiento createSeguimiento(Seguimiento seguimiento) {

        if (seguimiento.getAdopcion() == null && seguimiento.getFechaSeguimiento() == null && seguimiento.getImgUrl() == null) {
            throw new IllegalArgumentException("Ningún campo del seguimiento puede ser nulo");
        }



        return seguimientoRepository.save(seguimiento);


    }

    
    
}
