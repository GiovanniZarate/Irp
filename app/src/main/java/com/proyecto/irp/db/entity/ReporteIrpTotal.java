package com.proyecto.irp.db.entity;

public class ReporteIrpTotal {
    private String descripcion;
    private int totalirp;
    private int orden;
    private int salariominimo;
    private int porcentajepasado;
    private int porcentajebajo;


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTotalirp() {
        return totalirp;
    }

    public void setTotalirp(int totalirp) {
        this.totalirp = totalirp;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getSalariominimo() {
        return salariominimo;
    }

    public void setSalariominimo(int salariominimo) {
        this.salariominimo = salariominimo;
    }

    public int getPorcentajepasado() {
        return porcentajepasado;
    }

    public void setPorcentajepasado(int porcentajepasado) {
        this.porcentajepasado = porcentajepasado;
    }

    public int getPorcentajebajo() {
        return porcentajebajo;
    }

    public void setPorcentajebajo(int porcentajebajo) {
        this.porcentajebajo = porcentajebajo;
    }
}
