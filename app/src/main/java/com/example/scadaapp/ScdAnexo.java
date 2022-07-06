
package com.example.scadaapp;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScdAnexo {

    @SerializedName("idImagen")
    @Expose
    private Integer idImagen;
    @Expose
    private String urlAnexo;



    public ScdAnexo(Integer idImagen, String url) {
        this.idImagen = idImagen;
        this.urlAnexo = url;
    }

    public Integer getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Integer idImagen) {
        this.idImagen = idImagen;
    }

    public String getUrlAnexo() {
        return urlAnexo;
    }

    public void setUrlAnexo(String urlAnexo) {
        this.urlAnexo = urlAnexo;
    }

}
