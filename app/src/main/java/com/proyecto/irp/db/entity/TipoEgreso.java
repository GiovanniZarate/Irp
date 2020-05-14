package com.proyecto.irp.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tipoegreso")
public class TipoEgreso {
    @PrimaryKey(autoGenerate = true)
    private int idtipoegreso;
    private String descripcion;

    public TipoEgreso(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdtipoegreso(int idtipoegreso) {
        this.idtipoegreso = idtipoegreso;
    }

    public int getIdtipoegreso() {
        return idtipoegreso;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
