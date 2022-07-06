package com.example.scadaapp;

import android.content.Intent;

import java.time.DateTimeException;
import java.util.Date;

public class Log {

    Integer id;
    String idUsuario;
    String mac;
    String accion;
    Date fecha;
    String ip;


    public Log(Integer id,String idUsuario, String mac, String accion, Date fecha, String ip) {
        this.id=id;
        this.idUsuario = idUsuario;
        this.mac = mac;
        this.accion = accion;
        this.fecha = fecha;
        this.ip=ip;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
