package com.proyecto.irp.db.entity;


import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "cliente" ,indices = {@Index( value= {"ruc"}, unique = true)})
public class Cliente {
    @PrimaryKey(autoGenerate = true)
    private int idcliente;
    private String nombre;
    private String ruc;
    private int tipo;
    private int rucveri;
    private int div;

    public Cliente(String nombre, String ruc, int tipo, int rucveri, int div) {
        this.nombre = nombre;
        this.ruc = ruc;
        this.tipo = tipo;
        this.rucveri = rucveri;
        this.div = div;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public int getIdcliente() {
        return idcliente;
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
