package com.goucorporation.android.orientateuni.presentation.models;

public class Especialidad {
    private String nombre;
    private String descr;
    private String link;
    private String facultad;

    public Especialidad(String nombre, String descr, String link, String facultad) {
        this.nombre = nombre;
        this.descr = descr;
        this.link = link;
        this.facultad =facultad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
