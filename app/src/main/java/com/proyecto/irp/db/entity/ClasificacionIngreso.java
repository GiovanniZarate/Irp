package com.proyecto.irp.db.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "clasificacioningreso")
public class ClasificacionIngreso {
    @PrimaryKey(autoGenerate = true)
    private int idclasificacioningreso;
    private String descripcion;

    public ClasificacionIngreso(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdclasificacioningreso(int idclasificacioningreso) {
        this.idclasificacioningreso = idclasificacioningreso;
    }

    public int getIdclasificacioningreso() {
        return idclasificacioningreso;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
