package com.adopciones.server.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

public class Especie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre de la especie no puede estar vacío")
    private String nombre;

    //Relaciones
    @OneToMany(mappedBy = "especie", cascade = CascadeType.ALL)
    private List<Animal> animales = new ArrayList<>();

    @OneToMany(mappedBy = "especie", cascade = CascadeType.ALL)
    private List<Raza> razas = new ArrayList<>();

    public List<Animal> getAnimales() {
        return this.animales;
    }

    public void setAnimales(List<Animal> animales) {
        this.animales = animales;
    }

    public List<Raza> getRazas() {
        return this.razas;
    }

    public void setRazas(List<Raza> razas) {
        this.razas = razas;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
