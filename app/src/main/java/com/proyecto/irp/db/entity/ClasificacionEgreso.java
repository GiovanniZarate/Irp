package com.proyecto.irp.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "clasificacionegreso",foreignKeys = @ForeignKey(entity = TipoEgreso.class,
        parentColumns = "idtipoegreso",childColumns = "idtipoegreso"))

public class ClasificacionEgreso {
    @PrimaryKey(autoGenerate = true)
    private int idclasificacionegreso;
    private String descripcion;
    private int idtipoegreso;

    public ClasificacionEgreso(String descripcion, int idtipoegreso) {
        this.descripcion = descripcion;
        this.idtipoegreso = idtipoegreso;
    }

    public void setIdclasificacionegreso(int idclasificacionegreso) {
        this.idclasificacionegreso = idclasificacionegreso;
    }

    public int getIdclasificacionegreso() {
        return idclasificacionegreso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getIdtipoegreso() {
        return idtipoegreso;
    }
}
