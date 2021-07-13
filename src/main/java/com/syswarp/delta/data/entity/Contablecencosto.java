package com.syswarp.delta.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.syswarp.delta.data.AbstractEntity;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

@Entity
public class Contablecencosto  {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name="descripcion", length=50, nullable=false, unique=true)
    @NotBlank(message = "La descripcion no puede quedar vacia")
    private String descripcion;
    private String usuarioalt;
    private String usuarioact;
    private LocalDateTime fechaalt;
    private LocalDateTime fechaact;


    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
