package com.goucorporation.android.orientateuni.presentation.models;

import com.google.android.gms.maps.model.LatLng;

public class Lugar {
    public static final int TIPO_FACULTAD = 1;
    public static final int TIPO_OTRO = 2;
    public static final int TIPO_PUERTA = 3;

    private String codigo;
    private String nombre;
    private int resId;
    private LatLng latLng;
    private int tipo;

    public Lugar(String codigo,String nombre, int resId, LatLng latLng,int tipo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.resId = resId;
        this.latLng = latLng;
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo+":";
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return this.codigo+": "+this.nombre;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
