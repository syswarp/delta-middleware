package com.syswarp.delta.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.syswarp.delta.data.AbstractEntity;

import java.time.LocalDateTime;

@Entity
public class Contabletipificacion extends AbstractEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(name="tipo", length=50, nullable=false, unique=true)
    @NotBlank(message = "El tipo de cuenta no puede quedar vacio")
    private String tipo;
    private boolean mostrar;
    private boolean estotal;
    private String usuarioalt;
    private String usuarioact;
    private LocalDateTime fechaalt;
    private LocalDateTime fechaact;



    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public boolean isMostrar() {
        return mostrar;
    }
    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }
    public boolean isEstotal() {
        return estotal;
    }
    public void setEstotal(boolean estotal) {
        this.estotal = estotal;
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
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
