package com.proyecto.irp.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "clasificacioningreso")
public class ClasificacionIngreso {
    @PrimaryKey(autoGenerate = true)
    private int idclasificacioningreso;
    @ColumnInfo(name = "descripcion_clasificacioningreso")
    private String descripcionclasificacioningreso;


    public ClasificacionIngreso(String descripcionclasificacioningreso) {
        this.descripcionclasificacioningreso = descripcionclasificacioningreso;
    }

    public void setIdclasificacioningreso(int idclasificacioningreso) {
        this.idclasificacioningreso = idclasificacioningreso;
    }

    public int getIdclasificacioningreso() {
        return idclasificacioningreso;
    }

    public String getDescripcionclasificacioningreso() {
        return descripcionclasificacioningreso;
    }

    @NonNull
    @Override
    public String toString() {
        return descripcionclasificacioningreso;
    }
}
