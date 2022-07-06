package com.example.scadaapp;

public class Subestaciones {



    private String idSubestacion;
    private String nombreSubestacion;
    private String codigoEmpleado;
    private String custom;

    public Subestaciones(String idSubestacion, String nombreSubestacion, String codigoEmpleado) {
        this.idSubestacion = idSubestacion;
        this.nombreSubestacion = nombreSubestacion;
        this.codigoEmpleado = codigoEmpleado;
    }




    public Subestaciones(){

    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getIdSubestacion() {
        return idSubestacion;
    }

    public void setIdSubestacion(String idSubestacion) {
        this.idSubestacion = idSubestacion;
    }

    public String getNombreSubestacion() {
        return nombreSubestacion;
    }

    public void setNombreSubestacion(String nombreSubestacion) {
        this.nombreSubestacion = nombreSubestacion;
    }

    @Override
    public String toString(){
        this.custom=this.custom=nombreSubestacion;
        return custom;
    }
}
