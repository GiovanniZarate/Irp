package com.proyecto.irp.db.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity( tableName = "facturacompra",
        foreignKeys = {
                @ForeignKey(entity = ClasificacionEgreso.class,
                        parentColumns = "idclasificacionegreso",
                        childColumns = "id_clasificacionegreso"),
                @ForeignKey(entity = Proveedor.class,
                        parentColumns = "idproveedor",
                        childColumns = "id_proveedor"),
                @ForeignKey(entity = Contribuyente.class,
                        parentColumns = "idcontribuyente",
                        childColumns = "id_contribuyente"),
                @ForeignKey(entity = Ejercicio.class,
                        parentColumns = "_id",
                        childColumns = "id_ejercicio"),
                @ForeignKey(entity = TipoComprobante.class,
                        parentColumns = "idtipocomprobante",
                        childColumns = "id_comprobante"),
                @ForeignKey(entity = TipoEgreso.class,
                parentColumns = "idtipoegreso",
                childColumns = "id_tipoegresocompra")},

                indices = {
                            @Index(value= {"id_proveedor"}),
                            @Index(value= {"id_clasificacionegreso"}),
                            @Index(value= {"id_contribuyente"}),
                            @Index(value= {"id_ejercicio"}),
                            @Index(value= {"id_comprobante"}),
                            @Index(value= {"id_tipoegresocompra"})
                          }
        )

public class Facturacompra {

    @PrimaryKey(autoGenerate = true)
    public int idfacturacompra;

    public long fechacompra;

    public int id_proveedor;
    public int id_clasificacionegreso;
    public int id_contribuyente;
    public int id_ejercicio;
    public int id_comprobante;
    public int id_tipoegresocompra;

    public String nrofacturacompra;
    public int total_compra;
    public int exenta_compra;
    public int gravada10_compra;
    public int iva10_compra;
    public int gravada5_compra;
    public int iva5_compra;
    public String nro1_compra;
    public String nro2_compra;
    public String nro3_compra;

    public String dia_compra;
    public String mes_compra;
    public String anho_compra;


    public Facturacompra(long fechacompra, int id_proveedor, int id_clasificacionegreso, int id_contribuyente, int id_ejercicio, int id_comprobante, int id_tipoegresocompra, String nrofacturacompra, int total_compra, int exenta_compra, int gravada10_compra, int iva10_compra, int gravada5_compra, int iva5_compra, String nro1_compra, String nro2_compra, String nro3_compra, String dia_compra, String mes_compra, String anho_compra) {
        this.fechacompra = fechacompra;
        this.id_proveedor = id_proveedor;
        this.id_clasificacionegreso = id_clasificacionegreso;
        this.id_contribuyente = id_contribuyente;
        this.id_ejercicio = id_ejercicio;
        this.id_comprobante = id_comprobante;
        this.id_tipoegresocompra = id_tipoegresocompra;
        this.nrofacturacompra = nrofacturacompra;
        this.total_compra = total_compra;
        this.exenta_compra = exenta_compra;
        this.gravada10_compra = gravada10_compra;
        this.iva10_compra = iva10_compra;
        this.gravada5_compra = gravada5_compra;
        this.iva5_compra = iva5_compra;
        this.nro1_compra = nro1_compra;
        this.nro2_compra = nro2_compra;
        this.nro3_compra = nro3_compra;
        this.dia_compra = dia_compra;
        this.mes_compra = mes_compra;
        this.anho_compra = anho_compra;
    }

    public void setIdfacturacompra(int idfacturacompra) {
        this.idfacturacompra = idfacturacompra;
    }

    public int getIdfacturacompra() {
        return idfacturacompra;
    }

    public long getFechacompra() {
        return fechacompra;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public int getId_clasificacionegreso() {
        return id_clasificacionegreso;
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

    public int getId_tipoegresocompra() {
        return id_tipoegresocompra;
    }

    public String getNrofacturacompra() {
        return nrofacturacompra;
    }

    public int getTotal_compra() {
        return total_compra;
    }

    public int getExenta_compra() {
        return exenta_compra;
    }

    public int getGravada10_compra() {
        return gravada10_compra;
    }

    public int getIva10_compra() {
        return iva10_compra;
    }

    public int getGravada5_compra() {
        return gravada5_compra;
    }

    public int getIva5_compra() {
        return iva5_compra;
    }

    public String getNro1_compra() {
        return nro1_compra;
    }

    public String getNro2_compra() {
        return nro2_compra;
    }

    public String getNro3_compra() {
        return nro3_compra;
    }

    public String getDia_compra() {
        return dia_compra;
    }

    public String getMes_compra() {
        return mes_compra;
    }

    public String getAnho_compra() {
        return anho_compra;
    }



    @Embedded()
    public TipoComprobante tipoComprobante;
    //@Embedded
    //public TipoEgreso tipoEgreso;
    @Embedded
    public ClasificacionEgreso clasificacionEgreso;
    @Embedded
    public Proveedor proveedor;

}
