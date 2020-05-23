package com.proyecto.irp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity( tableName = "facturaventa",
        foreignKeys = {
                @ForeignKey(entity = ClasificacionIngreso.class,
                        parentColumns = "idclasificacioningreso",
                        childColumns = "id_clasificacioningreso"),
                @ForeignKey(entity = Cliente.class,
                        parentColumns = "idcliente",
                        childColumns = "id_cliente"),
                @ForeignKey(entity = Contribuyente.class,
                        parentColumns = "idcontribuyente",
                        childColumns = "id_contribuyente"),
                @ForeignKey(entity = Ejercicio.class,
                        parentColumns = "_id",
                        childColumns = "id_ejercicio"),
                @ForeignKey(entity = TipoComprobante.class,
                        parentColumns = "idtipocomprobante",
                        childColumns = "id_comprobante")},

                indices = {
                            @Index(value= {"id_cliente"}),
                            @Index(value= {"id_clasificacioningreso"}),
                            @Index(value= {"id_contribuyente"}),
                            @Index(value= {"id_ejercicio"}),
                            @Index(value= {"id_comprobante"})
                          }
        )

public class Facturaventa {

    @PrimaryKey(autoGenerate = true)
    public int idfacturaventa;

    public long fechaventa;

    public int id_cliente;
    public int id_clasificacioningreso;
    public int id_contribuyente;
    public int id_ejercicio;
    public int id_comprobante;

    public String nrofacturaventa;
    public int total_venta;
    public int exenta_venta;
    public int gravada10_venta;
    public int iva10_venta;
    public int gravada5_venta;
    public int iva5_venta;
    public String nro1_venta;
    public String nro2_venta;
    public String nro3_venta;

    public String dia_venta;
    public String mes_venta;
    public String anho_venta;


    public Facturaventa(long fechaventa, int id_cliente,
                        int id_clasificacioningreso, int id_contribuyente, int id_ejercicio,
                        int id_comprobante, String nrofacturaventa, int total_venta,
                        int exenta_venta, int gravada10_venta, int iva10_venta,
                        int gravada5_venta, int iva5_venta, String nro1_venta, String nro2_venta,
                        String nro3_venta, String dia_venta, String mes_venta, String anho_venta) {

        this.fechaventa = fechaventa;
        this.id_cliente = id_cliente;
        this.id_clasificacioningreso = id_clasificacioningreso;
        this.id_contribuyente = id_contribuyente;
        this.id_ejercicio = id_ejercicio;
        this.id_comprobante = id_comprobante;
        this.nrofacturaventa = nrofacturaventa;
        this.total_venta = total_venta;
        this.exenta_venta = exenta_venta;
        this.gravada10_venta = gravada10_venta;
        this.iva10_venta = iva10_venta;
        this.gravada5_venta = gravada5_venta;
        this.iva5_venta = iva5_venta;
        this.nro1_venta = nro1_venta;
        this.nro2_venta = nro2_venta;
        this.nro3_venta = nro3_venta;
        this.dia_venta = dia_venta;
        this.mes_venta = mes_venta;
        this.anho_venta = anho_venta;
    }

    public void setIdfacturaventa(int idfacturaventa) {
        this.idfacturaventa = idfacturaventa;
    }

    public int getIdfacturaventa() {
        return idfacturaventa;
    }

    public long getFechaventa() {
        return fechaventa;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public int getId_clasificacioningreso() {
        return id_clasificacioningreso;
    }

    public int getId_contribuyente() {
        return id_contribuyente;
    }

    public int getId_ejercicio() {
        return id_ejercicio;
    }

    public int getId_comprobante() {
        return id_comprobante;
    }

    public String getNrofacturaventa() {
        return nrofacturaventa;
    }

    public int getTotal_venta() {
        return total_venta;
    }

    public int getExenta_venta() {
        return exenta_venta;
    }

    public int getGravada10_venta() {
        return gravada10_venta;
    }

    public int getIva10_venta() {
        return iva10_venta;
    }

    public int getGravada5_venta() {
        return gravada5_venta;
    }

    public int getIva5_venta() {
        return iva5_venta;
    }

    public String getNro1_venta() {
        return nro1_venta;
    }

    public String getNro2_venta() {
        return nro2_venta;
    }

    public String getNro3_venta() {
        return nro3_venta;
    }

    public String getDia_venta() {
        return dia_venta;
    }

    public String getMes_venta() {
        return mes_venta;
    }

    public String getAnho_venta() {
        return anho_venta;
    }

    @Embedded()
    public TipoComprobante tipoComprobante;
    @Embedded
    public ClasificacionIngreso clasificacionIngreso;
    @Embedded
    public Cliente cliente;

}
