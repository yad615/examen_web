package com.tecsup.eval02.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "departamentos")
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddepartamentos")
    private int idDepartamentos;

    @Column(name = "nombre_departamentos")
    @NotEmpty(message = "El nombre del departamento no puede estar vacío")
    private String nombreDepartamentos;

    public Departamento() {}

    public Departamento(int idDepartamentos, String nombreDepartamentos) {
        this.idDepartamentos = idDepartamentos;
        this.nombreDepartamentos = nombreDepartamentos;
    }

    public int getIdDepartamentos() {
        return idDepartamentos;
    }

    public void setIdDepartamentos(int idDepartamentos) {
        this.idDepartamentos = idDepartamentos;
    }

    public String getNombreDepartamentos() {
        return nombreDepartamentos;
    }

    public void setNombreDepartamentos(String nombreDepartamentos) {
        this.nombreDepartamentos = nombreDepartamentos;
    }
}