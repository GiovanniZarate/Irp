package com.proyecto.irp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tipoegreso",indices = {@Index(value = {"idtipoegreso"},
        unique = true)})
public class TipoEgreso {
    @PrimaryKey(autoGenerate = true)
    public int idtipoegreso;

    @ColumnInfo(name = "descripcion_tipoegreso")
    public String descripciontipoegreso;

    public TipoEgreso(String descripciontipoegreso) {
        this.descripciontipoegreso = descripciontipoegreso;
    }

    public void setIdtipoegreso(int idtipoegreso) {
        this.idtipoegreso = idtipoegreso;
    }

    public int getIdtipoegreso() {
        return idtipoegreso;
    }

    public String getDescripciontipoegreso() {
        return descripciontipoegreso;
    }

    //implementar tostring para mostrar en combo

    @Override
    public String toString() {
        return  descripciontipoegreso ;
    }


    /* @Embedded(prefix = "ce_")
     public ClasificacionEgreso clasificacionEgreso;*/


    /*@Embedded
    ClasificacionEgreso clasificacionEgreso;

    // organization_name renamed during SELECT query w/ Organization.name AS organizaiton_name
    @ColumnInfo(name = "descripciontipoegreso")
    String tipoegresoName;*/


}
