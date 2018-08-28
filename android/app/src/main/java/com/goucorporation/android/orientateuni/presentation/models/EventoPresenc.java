package com.goucorporation.android.orientateuni.presentation.models;

import android.os.Parcel;
import android.os.Parcelable;

public class EventoPresenc implements Parcelable {
    private String titulo;
    private String descripcion;
    private String fecha;
    private Long date;
    private String hora;
    private String lugar;
    private String enlace;

    public EventoPresenc(String titulo, String descripcion, String fecha, String hora, String lugar, String enlace) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.lugar = lugar;
        this.enlace = enlace;
    }

    public EventoPresenc(String titulo, String descripcion, String fecha, String hora, String lugar) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.lugar = lugar;
        this.enlace = null;
    }

    public EventoPresenc(){}

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.titulo);
        dest.writeString(this.descripcion);
        dest.writeString(this.fecha);
        dest.writeValue(this.date);
        dest.writeString(this.hora);
        dest.writeString(this.lugar);
        dest.writeString(this.enlace);
    }

    protected EventoPresenc(Parcel in) {
        this.titulo = in.readString();
        this.descripcion = in.readString();
        this.fecha = in.readString();
        this.date = (Long) in.readValue(Long.class.getClassLoader());
        this.hora = in.readString();
        this.lugar = in.readString();
        this.enlace = in.readString();
    }

    public static final Creator<EventoPresenc> CREATOR = new Creator<EventoPresenc>() {
        @Override
        public EventoPresenc createFromParcel(Parcel source) {
            return new EventoPresenc(source);
        }

        @Override
        public EventoPresenc[] newArray(int size) {
            return new EventoPresenc[size];
        }
    };
}
