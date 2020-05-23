package com.proyecto.irp.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tipocomprobante")
public class TipoComprobante {
    @PrimaryKey(autoGenerate = true)
    private int idtipocomprobante;
    @ColumnInfo(name = "descripcion_tipocomprobante")
    private String descripciontipocomprobante;
    private int tipocpb;

    public TipoComprobante(String descripciontipocomprobante, int tipocpb) {
        this.descripciontipocomprobante = descripciontipocomprobante;
        this.tipocpb = tipocpb;
    }

    public void setIdtipocomprobante(int idtipocomprobante) {
        this.idtipocomprobante = idtipocomprobante;
    }

    public int getIdtipocomprobante() {
        return idtipocomprobante;
    }

    public String getDescripciontipocomprobante() {
        return descripciontipocomprobante;
    }

    public int getTipocpb() {
        return tipocpb;
    }

    @NonNull
    @Override
    public String toString() {
        return descripciontipocomprobante;
    }
}
