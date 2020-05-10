package com.proyecto.irp.db.entity;

import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Ejercicio.TABLE_NAME)
public class Ejercicio {
    /** The name of the Cheese table. */
    public static final String TABLE_NAME = "ejercicio";

    public static final String COLUMN_NAME = "anho";

    /** The name of the ID column. */
    public static final String COLUMN_ID = BaseColumns._ID;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(index = true, name = COLUMN_ID)
    public int idejercicio;

    @ColumnInfo(name = "anho")
    public int anho;
    @ColumnInfo(name = "total_ingreso")
    public  float total_ingreso;
    @ColumnInfo(name = "total_egreso")
    public float total_egreso;
    @ColumnInfo(name = "total_impuesto")
    public float total_impuesto;
    @ColumnInfo(name = "total_saldo")
    public float total_saldo;
    @ColumnInfo(name = "total_anticipo")
    public int total_anticipo;
    @ColumnInfo(name = "total_mora")
    public int total_mora;

    public Ejercicio(int idejercicio, int anho, float total_ingreso, float total_egreso, float total_impuesto, float total_saldo, int total_anticipo, int total_mora) {
        this.idejercicio = idejercicio;
        this.anho = anho;
        this.total_ingreso = total_ingreso;
        this.total_egreso = total_egreso;
        this.total_impuesto = total_impuesto;
        this.total_saldo = total_saldo;
        this.total_anticipo = total_anticipo;
        this.total_mora = total_mora;
    }

    public int getIdejercicio() {
        return idejercicio;
    }

    public void setIdejercicio(int idejercicio) {
        this.idejercicio = idejercicio;
    }

    public int getAnho() {
        return anho;
    }

    public void setAnho(int anho) {
        this.anho = anho;
    }

    public float getTotal_ingreso() {
        return total_ingreso;
    }

    public void setTotal_ingreso(float total_ingreso) {
        this.total_ingreso = total_ingreso;
    }

    public float getTotal_egreso() {
        return total_egreso;
    }

    public void setTotal_egreso(float total_egreso) {
        this.total_egreso = total_egreso;
    }

    public float getTotal_impuesto() {
        return total_impuesto;
    }

    public void setTotal_impuesto(float total_impuesto) {
        this.total_impuesto = total_impuesto;
    }

    public float getTotal_saldo() {
        return total_saldo;
    }

    public void setTotal_saldo(float total_saldo) {
        this.total_saldo = total_saldo;
    }

    public int getTotal_anticipo() {
        return total_anticipo;
    }

    public void setTotal_anticipo(int total_anticipo) {
        this.total_anticipo = total_anticipo;
    }

    public int getTotal_mora() {
        return total_mora;
    }

    public void setTotal_mora(int total_mora) {
        this.total_mora = total_mora;
    }
}
