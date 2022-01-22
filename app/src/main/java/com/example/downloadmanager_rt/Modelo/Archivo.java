package com.example.downloadmanager_rt.Modelo;

public class Archivo {

    private String fichero;
    private String fecha;
    private String ruta;

    public Archivo() {

    }

    public Archivo(String fichero, String fecha, String ruta) {
        this.fichero = fichero;
        this.fecha = fecha;
        this.ruta = ruta;
    }

    public String getFichero() {
        return fichero;
    }

    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}
