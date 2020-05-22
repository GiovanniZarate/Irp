package com.proyecto.irp.Config;

public class SessionUsuario {
    private String usuario;
    private String clave;
    private String ruc;
    private String nombrecontribuyente;
    private int anho;
    private int idejercicio;
    private int idcontribuyente;



    public SessionUsuario(String usuario, String clave, String ruc, String nombrecontribuyente,
                          int anho, int idejercicio, int idcontribuyente) {
        this.usuario = usuario;
        this.clave = clave;
        this.ruc = ruc;
        this.nombrecontribuyente = nombrecontribuyente;
        this.anho = anho;
        this.idejercicio = idejercicio;
        this.idcontribuyente = idcontribuyente;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombrecontribuyente() {
        return nombrecontribuyente;
    }

    public void setNombrecontribuyente(String nombrecontribuyente) {
        this.nombrecontribuyente = nombrecontribuyente;
    }

    public int getAnho() {
        return anho;
    }

    public void setAnho(int anho) {
        this.anho = anho;
    }

    public int getIdejercicio() {
        return idejercicio;
    }

    public void setIdejercicio(int idejercicio) {
        this.idejercicio = idejercicio;
    }

    public int getIdcontribuyente() {
        return idcontribuyente;
    }

    public void setIdcontribuyente(int idcontribuyente) {
        this.idcontribuyente = idcontribuyente;
    }
}
