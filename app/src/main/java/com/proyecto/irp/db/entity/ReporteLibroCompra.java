package com.proyecto.irp.db.entity;

public class ReporteLibroCompra {
    private int total_compra;
    private String tipodocumento;
    private int exenta_compra;
    private int gravada5_compra;
    private int iva5_compra;
    private int gravada10_compra;
    private int iva10_compra;
    private int importesiniva5;
    private int importesiniva10;
    private String nrofacturacompra;
    private String proveedor;
    private String rucproveedor;
    private String fechaventaformato;
    private int orden;

    public int getTotal_compra() {
        return total_compra;
    }

    public void setTotal_compra(int total_compra) {
        this.total_compra = total_compra;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public int getExenta_compra() {
        return exenta_compra;
    }

    public void setExenta_compra(int exenta_compra) {
        this.exenta_compra = exenta_compra;
    }

    public int getGravada5_compra() {
        return gravada5_compra;
    }

    public void setGravada5_compra(int gravada5_compra) {
        this.gravada5_compra = gravada5_compra;
    }

    public int getIva5_compra() {
        return iva5_compra;
    }

    public void setIva5_compra(int iva5_compra) {
        this.iva5_compra = iva5_compra;
    }

    public int getGravada10_compra() {
        return gravada10_compra;
    }

    public void setGravada10_compra(int gravada10_compra) {
        this.gravada10_compra = gravada10_compra;
    }

    public int getIva10_compra() {
        return iva10_compra;
    }

    public void setIva10_compra(int iva10_compra) {
        this.iva10_compra = iva10_compra;
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

    public String getNrofacturacompra() {
        return nrofacturacompra;
    }

    public void setNrofacturacompra(String nrofacturacompra) {
        this.nrofacturacompra = nrofacturacompra;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getRucproveedor() {
        return rucproveedor;
    }

    public void setRucproveedor(String rucproveedor) {
        this.rucproveedor = rucproveedor;
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
