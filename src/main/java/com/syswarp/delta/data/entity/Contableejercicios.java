package com.syswarp.delta.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.syswarp.delta.data.AbstractEntity;
import com.vaadin.flow.data.validator.DateRangeValidator;

import java.time.LocalDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

@Entity
//public class Contableejercicios extends AbstractEntity {
public class Contableejercicios  {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long id;

    @Column(name="ejercicio", length=100, nullable=false, unique=true)
    @NotBlank(message = "El nombre del ejercicio no puede quedar vacio")
    private String ejercicio;

    //@Column(name="fechadesde", nullable=false, unique=true)
    private LocalDate fechadesde;

    //@Column(name="fechadesde", nullable=false, unique=true)
    private LocalDate fechahasta;

    private boolean activo;
    private String usuarioalt;
    private String usuarioact;
    private LocalDateTime fechaalt;
    private LocalDateTime fechaact;

    public String getEjercicio() {
        return ejercicio;
    }
    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }
    public LocalDate getFechadesde() {
        return fechadesde;
    }
    public void setFechadesde(LocalDate fechadesde) {
        this.fechadesde = fechadesde;
    }
    public LocalDate getFechahasta() {
        return fechahasta;
    }
    public void setFechahasta(LocalDate fechahasta) {
        this.fechahasta = fechahasta;
    }
    public boolean isActivo() {
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    public String getUsuarioalt() {
        return usuarioalt;
    }
    public void setUsuarioalt(String usuarioalt) {
        this.usuarioalt = usuarioalt;
    }
    public String getUsuarioact() {
        return usuarioact;
    }
    public void setUsuarioact(String usuarioact) {
        this.usuarioact = usuarioact;
    }
    public LocalDateTime getFechaalt() {
        return fechaalt;
    }
    public void setFechaalt(LocalDateTime fechaalt) {
        this.fechaalt = fechaalt;
    }
    public LocalDateTime getFechaact() {
        return fechaact;
    }
    public void setFechaact(LocalDateTime fechaact) {
        this.fechaact = fechaact;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
