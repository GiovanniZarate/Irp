package com.proyecto.irp.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tipocomprobante")
public class TipoComprobante {
    @PrimaryKey(autoGenerate = true)
    private int idtipocomprobante;
    private String descripcion;
    private int tipocpb;

    public int getTipocpb() {
        return tipocpb;
    }

    public TipoComprobante(String descripcion, int tipocpb) {
        this.descripcion = descripcion;
        this.tipocpb = tipocpb;
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
