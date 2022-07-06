package com.example.scadaapp;

public class Alimentadores {
    public String codigoAlimentador;
    public String nombreAlimentador;

    private String custom;

    public Alimentadores(String codigoAlimentador, String nombreAlimentador) {
        this.codigoAlimentador = codigoAlimentador;
        this.nombreAlimentador = nombreAlimentador;

    }

    public Alimentadores() {
    }

    public String getCodigoAlimentador() {
        return codigoAlimentador;
    }

    public void setCodigoAlimentador(String codigoAlimentador) {
        this.codigoAlimentador = codigoAlimentador;
    }

    public String getNombreAlimentador() {
        return nombreAlimentador;
    }

    public void setNombreAlimentador(String nombreAlimentador) {
        this.nombreAlimentador = nombreAlimentador;
    }




    @Override
    public String toString(){
        this.custom=this.custom=nombreAlimentador;
        return custom;
    }
}
