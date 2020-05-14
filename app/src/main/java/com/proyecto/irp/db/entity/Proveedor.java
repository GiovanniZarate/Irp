package com.proyecto.irp.db.entity;


import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "proveedor" ,indices = {@Index( value= {"ruc"}, unique = true)})
public class Proveedor {
    @PrimaryKey(autoGenerate = true)
    private int idproveedor;
    private String nombre;
    private String ruc;
    private int tipo;
    private int rucveri;
    private int div;

    public Proveedor(String nombre, String ruc, int tipo, int rucveri, int div) {
        this.nombre = nombre;
        this.ruc = ruc;
        this.tipo = tipo;
        this.rucveri = rucveri;
        this.div = div;
    }

    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }

    public int getIdproveedor() {
        return idproveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRuc() {
        return ruc;
    }

    public int getTipo() {
        return tipo;
    }

    public int getRucveri() {
        return rucveri;
    }

    public int getDiv() {
        return div;
    }
}
