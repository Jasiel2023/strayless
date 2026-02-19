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

    private Date fecha_llegada;
    private String informacion;
    private String img_url;

    @Enumerated(EnumType.STRING)
    @Column(name = "salud_animal", length = 10, nullable = false)
    private SaludEnum salud_animal;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo_animal", length = 10, nullable = false)
    private SexoEnum sexo_animal;

    @Enumerated(EnumType.STRING)
    @Column(name = "disponibilidad", length = 10, nullable = false)
    private DisponibilidadEnum disponibilidad;

    // Relaciones
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especie_id")
    private Especie especie;

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

    public Date getFecha_llegada() {
        return this.fecha_llegada;
    }

    public void setFecha_llegada(Date fecha_llegada) {
        this.fecha_llegada = fecha_llegada;
    }

    public String getInformacion() {
        return this.informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public String getImg_url() {
        return this.img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public SaludEnum getSalud_animal() {
        return this.salud_animal;
    }

    public void setSalud_animal(SaludEnum salud_animal) {
        this.salud_animal = salud_animal;
    }

    public SexoEnum getSexo_animal() {
        return this.sexo_animal;
    }

    public void setSexo_animal(SexoEnum sexo_animal) {
        this.sexo_animal = sexo_animal;
    }

    public DisponibilidadEnum getDisponibilidad() {
        return this.disponibilidad;
    }

    public void setDisponibilidad(DisponibilidadEnum disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Especie getEspecie() {
        return this.especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public Adopcion getAdopcion() {
        return this.adopcion;
    }

    public void setAdopcion(Adopcion adopcion) {
        this.adopcion = adopcion;
    }

}
