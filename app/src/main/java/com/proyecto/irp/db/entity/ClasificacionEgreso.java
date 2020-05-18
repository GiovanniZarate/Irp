package com.proyecto.irp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;



/*
*  public class Address {
        public String street;
        public String state;
        public String city;

        @ColumnInfo(name = "post_code") public int postCode;
    }

    @Entity
    public class User {
        @PrimaryKey public int id;

        public String firstName;

        @Embedded public Address address;
    }
* */
/*@Entity(tableName = "clasificacionegreso",foreignKeys = @ForeignKey(entity = TipoEgreso.class,
        parentColumns = "idtipoegreso",childColumns = "idtipoegreso"))*/

@Entity(foreignKeys = @ForeignKey(entity = TipoEgreso.class,
                                 parentColumns = "idtipoegreso",
                                 childColumns = "id_tipoegreso"))



public class ClasificacionEgreso {
    @PrimaryKey(autoGenerate = true)
    public int idclasificacionegreso;

    public String descripcion;

    @ColumnInfo(name = "id_tipoegreso")
    public int codtipoegreso;

    /*@Ignore
    public String descripcion_tipoegreso;

    public String getDescripcion_tipoegreso() {
        return descripcion_tipoegreso;
    }*/

    /* @Ignore
    public TipoEgreso tipoEgreso;

    public TipoEgreso getTipoEgreso() {
        return tipoEgreso;
    }

    public void setTipoEgreso(TipoEgreso tipoEgreso) {
        this.tipoEgreso = tipoEgreso;
    }*/

    public ClasificacionEgreso(String descripcion, int codtipoegreso) {
        this.descripcion = descripcion;
        this.codtipoegreso = codtipoegreso;
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

    public int getCodtipoegreso() {
        return codtipoegreso;
    }




   @Embedded
   public TipoEgreso tipoEgreso;
/*
    public TipoEgreso getTipoEgreso() {
        return tipoEgreso;
    }*/




}
