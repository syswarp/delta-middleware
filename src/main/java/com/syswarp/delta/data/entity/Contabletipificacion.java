package com.syswarp.delta.data.entity;

import javax.persistence.Entity;

import com.syswarp.delta.data.AbstractEntity;

@Entity
public class Contabletipificacion extends AbstractEntity {

    private Integer idtipocuenta;
    private String tipo;
    private boolean mostrar;
    private boolean estotal;

    public Integer getIdtipocuenta() {
        return idtipocuenta;
    }
    public void setIdtipocuenta(Integer idtipocuenta) {
        this.idtipocuenta = idtipocuenta;
    }
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

}
