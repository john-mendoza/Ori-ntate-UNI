package com.goucorporation.android.orientateuni.presentation.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class EventoMes implements Parcelable {
    private List<EventoPresenc> eventos;
    public EventoMes(){}

    public List<EventoPresenc> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoPresenc> eventos) {
        this.eventos = eventos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.eventos);
    }

    protected EventoMes(Parcel in) {
        this.eventos = in.createTypedArrayList(EventoPresenc.CREATOR);
    }

    public static final Creator<EventoMes> CREATOR = new Creator<EventoMes>() {
        @Override
        public EventoMes createFromParcel(Parcel source) {
            return new EventoMes(source);
        }

        @Override
        public EventoMes[] newArray(int size) {
            return new EventoMes[size];
        }
    };
}
