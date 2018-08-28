package com.goucorporation.android.orientateuni.presentation.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.goucorporation.android.orientateuni.presentation.models.EventoMes;
import com.goucorporation.android.orientateuni.presentation.models.EventoPresenc;
import com.goucorporation.android.orientateuni.presentation.views.views.EventoPresencView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EventoPresencPresenter extends BasePresenter<EventoPresencView> {
    private final FirebaseFirestore db;
    private long currentTime;
    private static final String TAG = "EventoPresenc";

    public EventoPresencPresenter(EventoPresencView view) {
        super(view);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        currentTime = calendar.getTimeInMillis()/1000;
        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
    }

    public void consultarEventos(String facultad){
        view.mostrarLoading();
        db.collection("eventos")
                .whereGreaterThanOrEqualTo("facultades."+facultad,currentTime)
                .orderBy("facultades."+facultad, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            procesarEventos(task);
                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                            view.ocultarLoading();
                        }
                    }
                });
    }

    private void procesarEventos(Task<QuerySnapshot> task) {
        EventoMes eventoMes = new EventoMes();
        List<EventoPresenc> eventoPresencList = new ArrayList<>();
        for (QueryDocumentSnapshot document : task.getResult()) {
            EventoPresenc evento = new EventoPresenc();
            evento.setTitulo(document.getString("titulo"));
            evento.setDescripcion(document.getString("descripcion"));
            evento.setFecha(document.getString("fecha"));
            evento.setDate(document.getLong("date"));
            evento.setHora(document.getString("horario"));
            evento.setLugar(document.getString("lugar"));
            evento.setEnlace(document.getString("enlace"));
            if(evento.getEnlace().length()==0)
                evento.setEnlace(null);

            eventoPresencList.add(evento);
        }
        eventoMes.setEventos(eventoPresencList);

        view.mostrarEventos(eventoMes);
        view.ocultarLoading();
    }
}
