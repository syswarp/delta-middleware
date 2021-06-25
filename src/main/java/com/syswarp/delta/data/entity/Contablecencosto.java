package com.syswarp.delta.data.entity;

import javax.persistence.Entity;

import com.syswarp.delta.data.AbstractEntity;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

@Entity
public class Contablecencosto extends AbstractEntity {

    private Integer idcencosto;
    private String descripcion;
    private String usuarioalt;
    private String usuarioact;
    private LocalDateTime fechaalt;
    private LocalDateTime fechaact;

    public Integer getIdcencosto() {
        return idcencosto;
    }
    public void setIdcencosto(Integer idcencosto) {
        this.idcencosto = idcencosto;
    }
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

}
