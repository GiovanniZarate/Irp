package com.proyecto.irp.db.entity;

public class ReporteLibroVenta {
    private int total_venta;
    private String tipodocumento;
    private int exenta_venta;
    private int gravada5_venta;
    private int iva5_venta;
    private int gravada10_venta;
    private int iva10_venta;
    private int importesiniva5;
    private int importesiniva10;
    private String nrofacturaventa;
    private String cliente;
    private String ruccliente;
    private String fechaventaformato;
    private int orden;



    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public int getTotal_venta() {
        return total_venta;
    }

    public void setTotal_venta(int total_venta) {
        this.total_venta = total_venta;
    }

    public int getExenta_venta() {
        return exenta_venta;
    }

    public void setExenta_venta(int exenta_venta) {
        this.exenta_venta = exenta_venta;
    }

    public int getGravada5_venta() {
        return gravada5_venta;
    }

    public void setGravada5_venta(int gravada5_venta) {
        this.gravada5_venta = gravada5_venta;
    }

    public int getIva5_venta() {
        return iva5_venta;
    }

    public void setIva5_venta(int iva5_venta) {
        this.iva5_venta = iva5_venta;
    }

    public int getGravada10_venta() {
        return gravada10_venta;
    }

    public void setGravada10_venta(int gravada10_venta) {
        this.gravada10_venta = gravada10_venta;
    }

    public int getIva10_venta() {
        return iva10_venta;
    }

    public void setIva10_venta(int iva10_venta) {
        this.iva10_venta = iva10_venta;
    }

    public int getImportesiniva5() {
        return importesiniva5;
    }

    public void setImportesiniva5(int importesiniva5) {
        this.importesiniva5 = importesiniva5;
    }

    public int getImportesiniva10() {
        return importesiniva10;
    }

    public void setImportesiniva10(int importesiniva10) {
        this.importesiniva10 = importesiniva10;
    }

    public String getNrofacturaventa() {
        return nrofacturaventa;
    }

    public void setNrofacturaventa(String nrofacturaventa) {
        this.nrofacturaventa = nrofacturaventa;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getRuccliente() {
        return ruccliente;
    }

    public void setRuccliente(String ruccliente) {
        this.ruccliente = ruccliente;
    }

    public String getFechaventaformato() {
        return fechaventaformato;
    }

    public void setFechaventaformato(String fechaventaformato) {
        this.fechaventaformato = fechaventaformato;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}
