package com.proyecto.irp.db.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

//@Entity(indices = {@Index("name"), @Index(value = {"last_name", "address"})})
@Entity(tableName = "contribuyente" ,indices = {@Index( value= {"documento"}, unique = true)})
public class Contribuyente {

    @PrimaryKey(autoGenerate = true)
    private int idcontribuyente;
    private String documento;
    private String ruc;
    private String nombres;
    private String contrasena;

    public Contribuyente(String documento, String ruc, String nombres, String contrasena) {
        this.documento = documento;
        this.ruc = ruc;
        this.nombres = nombres;
        this.contrasena = contrasena;
    }

    public void setIdcontribuyente(int idcontribuyente) {
        this.idcontribuyente = idcontribuyente;
    }

    public int getIdcontribuyente() {
        return idcontribuyente;
    }

    public String getDocumento() {
        return documento;
    }

    public String getRuc() {
        return ruc;
    }

    public String getNombres() {
        return nombres;
    }

    public String getContrasena() {
        return contrasena;
    }
}
