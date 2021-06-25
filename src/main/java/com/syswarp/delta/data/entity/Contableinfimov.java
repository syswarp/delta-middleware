package com.syswarp.delta.data.entity;

import javax.persistence.Entity;

import com.syswarp.delta.data.AbstractEntity;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

@Entity
public class Contableinfimov extends AbstractEntity {

    private Integer idasientodet;
    private Integer idasiento;
    private Integer renglon;
    private Integer idcuenta;
    private Integer tipomov;
    private Integer importe;
    private String detalle;
    private Integer asiento_nuevo;
    private Integer idcencost1;
    private Integer idcentcost2;
    private String usuarioalt;
    private String usuarioact;
    private LocalDateTime fechaalt;
    private LocalDateTime fechaact;

    public Integer getIdasientodet() {
        return idasientodet;
    }
    public void setIdasientodet(Integer idasientodet) {
        this.idasientodet = idasientodet;
    }
    public Integer getIdasiento() {
        return idasiento;
    }
    public void setIdasiento(Integer idasiento) {
        this.idasiento = idasiento;
    }
    public Integer getRenglon() {
        return renglon;
    }
    public void setRenglon(Integer renglon) {
        this.renglon = renglon;
    }
    public Integer getIdcuenta() {
        return idcuenta;
    }
    public void setIdcuenta(Integer idcuenta) {
        this.idcuenta = idcuenta;
    }
    public Integer getTipomov() {
        return tipomov;
    }
    public void setTipomov(Integer tipomov) {
        this.tipomov = tipomov;
    }
    public Integer getImporte() {
        return importe;
    }
    public void setImporte(Integer importe) {
        this.importe = importe;
    }
    public String getDetalle() {
        return detalle;
    }
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    public Integer getAsiento_nuevo() {
        return asiento_nuevo;
    }
    public void setAsiento_nuevo(Integer asiento_nuevo) {
        this.asiento_nuevo = asiento_nuevo;
    }
    public Integer getIdcencost1() {
        return idcencost1;
    }
    public void setIdcencost1(Integer idcencost1) {
        this.idcencost1 = idcencost1;
    }
    public Integer getIdcentcost2() {
        return idcentcost2;
    }
    public void setIdcentcost2(Integer idcentcost2) {
        this.idcentcost2 = idcentcost2;
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
