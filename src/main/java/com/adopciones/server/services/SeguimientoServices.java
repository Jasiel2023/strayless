package com.adopciones.server.services;

import com.adopciones.server.models.Seguimiento;
import com.adopciones.server.repository.SeguimientoRepository;

import jakarta.transaction.Transactional;

public class SeguimientoServices {
    
    private final SeguimientoRepository seguimientoRepository;
    
    SeguimientoServices(SeguimientoRepository seguimientoRepository) {
        this.seguimientoRepository = seguimientoRepository;
    }

   /* @Transactional
    public Seguimiento createSeguimiento() {


        Seguimiento seguimientoNew = new Seguimiento();



    }*/

    
    
}
