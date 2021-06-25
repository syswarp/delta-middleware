package com.syswarp.delta.data.entity;

import javax.persistence.Entity;

import com.syswarp.delta.data.AbstractEntity;
import java.time.LocalDate;
import java.time.LocalDate;
import java.time.LocalDate;

@Entity
public class Contableasientos extends AbstractEntity {

    private Integer idasiento;
    private Integer idejercicio;
    private LocalDate fecha;
    private String leyenda;
    private Integer nroasiento;
    private Integer idtipoasiento;
    private Integer asiento_nuevo;
    private String sistema;
    private String usuarioalt;
    private String usuarioact;
    private LocalDate fechaalt;
    private LocalDate fechaact;

    public Integer getIdasiento() {
        return idasiento;
    }
    public void setIdasiento(Integer idasiento) {
        this.idasiento = idasiento;
    }
    public Integer getIdejercicio() {
        return idejercicio;
    }
    public void setIdejercicio(Integer idejercicio) {
        this.idejercicio = idejercicio;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public String getLeyenda() {
        return leyenda;
    }
    public void setLeyenda(String leyenda) {
        this.leyenda = leyenda;
    }
    public Integer getNroasiento() {
        return nroasiento;
    }
    public void setNroasiento(Integer nroasiento) {
        this.nroasiento = nroasiento;
    }
    public Integer getIdtipoasiento() {
        return idtipoasiento;
    }
    public void setIdtipoasiento(Integer idtipoasiento) {
        this.idtipoasiento = idtipoasiento;
    }
    public Integer getAsiento_nuevo() {
        return asiento_nuevo;
    }
    public void setAsiento_nuevo(Integer asiento_nuevo) {
        this.asiento_nuevo = asiento_nuevo;
    }
    public String getSistema() {
        return sistema;
    }
    public void setSistema(String sistema) {
        this.sistema = sistema;
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
    public LocalDate getFechaalt() {
        return fechaalt;
    }
    public void setFechaalt(LocalDate fechaalt) {
        this.fechaalt = fechaalt;
    }
    public LocalDate getFechaact() {
        return fechaact;
    }
    public void setFechaact(LocalDate fechaact) {
        this.fechaact = fechaact;
    }

}
