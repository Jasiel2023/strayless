package com.adopciones.server.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.adopciones.server.enums.RolEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;


@Entity
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;
    @NotBlank(message = "El teléfono no puede estar vacío")
    private String telefono;
    private Date fecha_nacimiento;
    @NotBlank(message = "La cédula no puede estar vacía")
    private String cedula;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "rol", nullable = false)
    private RolEnum rol;

    //Relaciones 
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Adopcion> adopciones = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Cuenta> cuentas = new ArrayList<>();

    public List<Cuenta> getCuentas() {
        return this.cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public List<Adopcion> getAdopciones() {
        return this.adopciones;
    }

    public void setAdopciones(List<Adopcion> adopciones) {
        this.adopciones = adopciones;
    }

    public RolEnum getRol() {
        return this.rol;
    }

    public void setRol(RolEnum rol) {
        this.rol = rol;
    }


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

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFecha_nacimiento() {
        return this.fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getCedula() {
        return this.cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

}
