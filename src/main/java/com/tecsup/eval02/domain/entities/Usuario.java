package com.tecsup.eval02.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.sql.Timestamp;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuarios")
    private Integer idUsuarios;

    @Column(name = "nombre_completo")
    @NotEmpty(message = "El nombre completo no puede estar vacío")
    private String nombreCompleto;

    @Column(name = "email")
    @Email(message = "Debe ser un correo electrónico válido")
    @NotEmpty(message = "El email no puede estar vacío")
    private String email;

    @Column(name = "contraseña")
    @NotEmpty(message = "La contraseña no puede estar vacía")
    private String contraseña;

    @Column(name = "fecha_registro", updatable = false)
    private Timestamp fechaRegistro;

    public Usuario() {
        this.fechaRegistro = new Timestamp(System.currentTimeMillis());
    }

    public Usuario(Integer idUsuarios, String nombreCompleto, String email, String contraseña, Timestamp fechaRegistro) { // Cambiar aquí también
        this.idUsuarios = idUsuarios;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.contraseña = contraseña;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdUsuarios() { // Cambiado a Integer
        return idUsuarios;
    }

    public void setIdUsuarios(Integer idUsuarios) { // Cambiado a Integer
        this.idUsuarios = idUsuarios;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
