package com.goucorporation.android.orientateuni.presentation.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class EventoDataMes implements Parcelable {
    private List<String> tituloMeses;
    private List<EventoMes> eventosPorMeses;

    public EventoDataMes(){}

    public List<String> getTituloMeses() {
        return tituloMeses;
    }

    public void setTituloMeses(List<String> tituloMeses) {
        this.tituloMeses = tituloMeses;
    }

    public List<EventoMes> getEventosPorMeses() {
        return eventosPorMeses;
    }

    public void setEventosPorMeses(List<EventoMes> eventosPorMeses) {
        this.eventosPorMeses = eventosPorMeses;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.tituloMeses);
        dest.writeTypedList(this.eventosPorMeses);
    }

    protected EventoDataMes(Parcel in) {
        this.tituloMeses = in.createStringArrayList();
        this.eventosPorMeses = in.createTypedArrayList(EventoMes.CREATOR);
    }

    public static final Creator<EventoDataMes> CREATOR = new Creator<EventoDataMes>() {
        @Override
        public EventoDataMes createFromParcel(Parcel source) {
            return new EventoDataMes(source);
        }

        @Override
        public EventoDataMes[] newArray(int size) {
            return new EventoDataMes[size];
        }
    };
}
