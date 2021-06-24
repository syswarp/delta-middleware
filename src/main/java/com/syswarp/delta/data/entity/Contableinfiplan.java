package com.syswarp.delta.data.entity;

import javax.persistence.Entity;

import com.syswarp.delta.data.AbstractEntity;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

@Entity
public class Contableinfiplan extends AbstractEntity {

    private Integer idejercicio;
    private Integer idcuenta;
    private String cuenta;
    private boolean imputable;
    private boolean ajustable;
    private boolean resultado;
    private Integer nivel;
    private Integer idcentrocosto1;
    private Integer idcentrocosto2;
    private String usuarioalt;
    private String usuarioact;
    private LocalDateTime fechaalt;
    private LocalDateTime fechaact;

    public Integer getIdejercicio() {
        return idejercicio;
    }
    public void setIdejercicio(Integer idejercicio) {
        this.idejercicio = idejercicio;
    }
    public Integer getIdcuenta() {
        return idcuenta;
    }
    public void setIdcuenta(Integer idcuenta) {
        this.idcuenta = idcuenta;
    }
    public String getCuenta() {
        return cuenta;
    }
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
    public boolean isImputable() {
        return imputable;
    }
    public void setImputable(boolean imputable) {
        this.imputable = imputable;
    }
    public boolean isAjustable() {
        return ajustable;
    }
    public void setAjustable(boolean ajustable) {
        this.ajustable = ajustable;
    }
    public boolean isResultado() {
        return resultado;
    }
    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }
    public Integer getNivel() {
        return nivel;
    }
    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }
    public Integer getIdcentrocosto1() {
        return idcentrocosto1;
    }
    public void setIdcentrocosto1(Integer idcentrocosto1) {
        this.idcentrocosto1 = idcentrocosto1;
    }
    public Integer getIdcentrocosto2() {
        return idcentrocosto2;
    }
    public void setIdcentrocosto2(Integer idcentrocosto2) {
        this.idcentrocosto2 = idcentrocosto2;
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
