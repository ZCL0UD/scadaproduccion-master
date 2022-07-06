package com.example.scadaapp;

public class Usuarios {

    private String idUsuario;
    private String clave;
    private String custom;
    private String nombres;
    private String apellidos;

    public Usuarios(){

    }

    public Usuarios(String idUsuario, String clave, String nombres, String apellidos) {
        this.idUsuario = idUsuario;
        this.clave = clave;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }



    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }


    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String toString(){
        this.custom=this.custom= nombres +" "+apellidos;
        return custom;
    }
}
