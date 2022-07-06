package com.example.scadaapp;

public class Eventos {

    public Integer idEvento;
    public String descripcionEvento;
    private String custom;



    public Eventos(){

    }

    public Eventos(Integer idEvento, String descripcion) {
        this.idEvento = idEvento;
        this.descripcionEvento = descripcion;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getDescripcion() {
        return descripcionEvento;
    }

    public void setDescripcion(String descripcion) {
        this.descripcionEvento = descripcion;
    }

    @Override
    public String toString(){
        this.custom=this.custom=descripcionEvento;
        return custom;
    }
}
