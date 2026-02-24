package com.adopciones.server.models;



import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Raza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

   
    @NotBlank(message = "El nombre de la raza no puede estar vacío")
    private String nombre;

    //Relacion
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "especie_id")
    private Especie especie;

    @OneToMany(mappedBy = "raza", cascade = CascadeType.ALL)
    private List<Animal> animales = new ArrayList<>();

    public List<Animal> getAnimales() {
        return this.animales;
    }

    public void setAnimales(List<Animal> animales) {
        this.animales = animales;
    }

     public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Especie getEspecie() {
        return this.especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
