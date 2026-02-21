package com.adopciones.server.models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Seguimiento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String img_url;
    private String observaciones;
    private Date fecha_seguimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adopcion_id")
    private Adopcion adopcion;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg_url() {
        return this.img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFecha_seguimiento() {
        return this.fecha_seguimiento;
    }

    public void setFecha_seguimiento(Date fecha_seguimiento) {
        this.fecha_seguimiento = fecha_seguimiento;
    }

}
