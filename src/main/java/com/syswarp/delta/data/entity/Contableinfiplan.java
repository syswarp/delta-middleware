package com.syswarp.delta.data.entity;

import javax.persistence.*;

import com.syswarp.delta.data.AbstractEntity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

@Entity
@Table(name="Contableinfiplan")


public class Contableinfiplan implements Serializable {
// id compuesto!

    @Id
    @Column
    private long idcuenta;


// relaciones
   @ManyToOne
   @JoinColumn(name="idcentrocosto1")
   private Contablecencosto cc1;


    @ManyToOne
    @JoinColumn(name="idcentrocosto2")
    private Contablecencosto cc2;




    private String cuenta;
    private boolean imputable;
    private boolean ajustable;
    private boolean resultado;
    private Integer nivel;
   // private Integer idcentrocosto1;
   // private Integer idcentrocosto2;
    private String usuarioalt;
    private String usuarioact;
    private LocalDateTime fechaalt;
    private LocalDateTime fechaact;

    public long getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(long idcuenta) {
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

    ///////


    public Contablecencosto getCc1() {
        return cc1;
    }

    public void setCc1(Contablecencosto cc1) {
        this.cc1 = cc1;
    }

    public Contablecencosto getCc2() {
        return cc2;
    }

    public void setCc2(Contablecencosto cc2) {
        this.cc2 = cc2;
    }


}
