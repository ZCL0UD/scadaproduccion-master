
package com.example.scadaapp;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Incidente {

    @SerializedName("idIncidente")
    @Expose
    private Integer idIncidente;

    @SerializedName("codigoAlimentador")
    @Expose
    private String codigoAlimentador;

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

    @SerializedName("maniobrasRealizadas")
    @Expose
    private String maniobras;



    @Expose
    private List<ScdAnexo> scdAnexos = null;

    @SerializedName("scdEvento")
    @Expose
    private Eventos scdEvento;


    @Expose
    Integer idJefeZonal;

    @Expose
    String codigoIncidencia;


    public Incidente(String codigoAlimentador, String descripcion, String fechaEvento, String fechaIngreso,
                     Integer idJefe, Integer idResponsable, String nroPoste, String maniobras, Integer idJefeZonal, List<ScdAnexo> scdAnexos, Eventos scdEvento) {

        this.codigoAlimentador= codigoAlimentador;
        this.descripcion = descripcion;
        this.fechaEvento = fechaEvento;
        this.fechaIngreso = fechaIngreso;
        this.idJefe = idJefe;
        this.idResponsable = idResponsable;
        this.nroPoste = nroPoste;
        this.maniobras=maniobras;
        this.scdAnexos = scdAnexos;
        this.scdEvento = scdEvento;
        this.idJefeZonal= idJefeZonal;

    }

    public String getCodigoAlimentador() {
        return codigoAlimentador;
    }

    public void setCodigoAlimentador(String codigoAlimentador) {
        this.codigoAlimentador = codigoAlimentador;
    }

    public Integer getIdJefeZonal() {
        return idJefeZonal;
    }

    public void setIdJefeZonal(Integer idJefeZonal) {
        this.idJefeZonal = idJefeZonal;
    }

    public String getManiobras() {
        return maniobras;
    }

    public void setManiobras(String maniobras) {
        this.maniobras = maniobras;
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

    public List<ScdAnexo> getScdAnexos() {
        return scdAnexos;
    }

    public void setScdAnexos(List<ScdAnexo> scdAnexos) {
        this.scdAnexos = scdAnexos;
    }

    public Eventos getScdEvento() {
        return scdEvento;
    }

    public void setScdEvento(Eventos scdEvento) {
        this.scdEvento = scdEvento;
    }

    public String getCodigoIncidencia() {
        return codigoIncidencia;
    }

    public void setCodigoIncidencia(String codigoIncidencia) {
        this.codigoIncidencia = codigoIncidencia;
    }
}
