package com.proyecto.irp.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tipocomprobante")
public class TipoComprobante {
    @PrimaryKey(autoGenerate = true)
    private int idtipocomprobante;
    private String descripcion;

    public TipoComprobante(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdtipocomprobante(int idtipocomprobante) {
        this.idtipocomprobante = idtipocomprobante;
    }

    public int getIdtipocomprobante() {
        return idtipocomprobante;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
