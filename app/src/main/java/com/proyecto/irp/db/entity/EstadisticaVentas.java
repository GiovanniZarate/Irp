package com.proyecto.irp.db.entity;

public class EstadisticaVentas {
    private int anho;
    private int totalventa;
    private int orden;



    public int getAnho() {
        return anho;
    }

    public void setAnho(int anho) {
        this.anho = anho;
    }

    public int getTotalventa() {
        return totalventa;
    }
    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
    public void setTotalventa(int totalventa) {
        this.totalventa = totalventa;
    }
}
