package com.adopciones.server.models;

import java.sql.Date;

import com.adopciones.server.enums.SaludEnum;
import com.adopciones.server.enums.SexoEnum;
import com.adopciones.server.enums.DisponibilidadEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del animal no puede estar vacío")
    private String nombre;

    private Date fechaLlegada;
    private String informacion;
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "salud_animal", length = 10, nullable = false)
    private SaludEnum saludAnimal;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo_animal", length = 10, nullable = false)
    private SexoEnum sexoAnimal;

    @Enumerated(EnumType.STRING)
    @Column(name = "disponibilidad", length = 10, nullable = false)
    private DisponibilidadEnum disponibilidad;

    // Relaciones
    @OneToOne
    @JoinColumn(name = "raza_id")
    private Raza raza;

    @OneToOne
    @JoinColumn(name = "adopcion_id")
    private Adopcion adopcion;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaLlegada() {
        return this.fechaLlegada;
    }

    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public String getInformacion() {
        return this.informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public SaludEnum getSaludAnimal() {
        return this.saludAnimal;
    }

    public void setSaludAnimal(SaludEnum saludAnimal) {
        this.saludAnimal = saludAnimal;
    }

    public SexoEnum getSexoAnimal() {
        return this.sexoAnimal;
    }

    public void setSexoAnimal(SexoEnum sexoAnimal) {
        this.sexoAnimal = sexoAnimal;
    }

    public DisponibilidadEnum getDisponibilidad() {
        return this.disponibilidad;
    }

    public void setDisponibilidad(DisponibilidadEnum disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Raza getRaza() {
        return this.raza;
    }

    public void setRaza(Raza raza) {
        this.raza = raza;
    }

    public Adopcion getAdopcion() {
        return this.adopcion;
    }

    public void setAdopcion(Adopcion adopcion) {
        this.adopcion = adopcion;
    }

}
