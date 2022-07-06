package com.example.scadaapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Lista {

    @SerializedName("idIncidente")
    @Expose
    private Integer idIncidente;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("fechaEvento")
    @Expose
    private String fechaEvento;
    @SerializedName("fechaIngreso")
    @Expose
    private String fechaIngreso;
    @SerializedName("idJefe")
    @Expose
    private Integer idJefe;
    @SerializedName("idResponsable")
    @Expose
    private Integer idResponsable;
    @SerializedName("nroPoste")
    @Expose
    private String nroPoste;

    @SerializedName("maniobra")
    @Expose
    private String maniobra;


    @SerializedName("nombreAlimentador")
    @Expose
    private String nombreAlimentador;

    @SerializedName("descripcionEvento")
    @Expose
    private String descripcionEvento;


    private String codigoIncidencia;

    public Lista(Integer idIncidente, String descripcion, String fechaEvento, String fechaIngreso, Integer idJefe, Integer idResponsable, String nroPoste, String nombreAlimentador, String descripcionEvento) {
        this.idIncidente = idIncidente;
        this.descripcion = descripcion;
        this.fechaEvento = fechaEvento;
        this.fechaIngreso = fechaIngreso;
        this.idJefe = idJefe;
        this.idResponsable = idResponsable;
        this.nroPoste = nroPoste;
        this.nombreAlimentador = nombreAlimentador;
        this.descripcionEvento = descripcionEvento;
    }

    public Lista(){

    }
    public Integer getIdIncidente() {
        return idIncidente;
    }

    public void setIdIncidente(Integer idIncidente) {
        this.idIncidente = idIncidente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdJefe() {
        return idJefe;
    }

    public void setIdJefe(Integer idJefe) {
        this.idJefe = idJefe;
    }

    public Integer getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(Integer idResponsable) {
        this.idResponsable = idResponsable;
    }

    public String getNroPoste() {
        return nroPoste;
    }

    public void setNroPoste(String nroPoste) {
        this.nroPoste = nroPoste;
    }

    public String getNombreAlimentador() {
        return nombreAlimentador;
    }

    public void setNombreAlimentador(String nombreAlimentador) {
        this.nombreAlimentador = nombreAlimentador;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public String getManiobra() {
        return maniobra;
    }

    public void setManiobra(String maniobra) {
        this.maniobra = maniobra;
    }

    public String getCodigoIncidencia() {
        return codigoIncidencia;
    }

    public void setCodigoIncidencia(String codigoIncidencia) {
        this.codigoIncidencia = codigoIncidencia;
    }
}
