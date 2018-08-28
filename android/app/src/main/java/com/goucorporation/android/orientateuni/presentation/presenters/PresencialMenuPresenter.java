package com.goucorporation.android.orientateuni.presentation.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.goucorporation.android.orientateuni.presentation.models.EventoDataMes;
import com.goucorporation.android.orientateuni.presentation.models.EventoMes;
import com.goucorporation.android.orientateuni.presentation.models.EventoPresenc;
import com.goucorporation.android.orientateuni.presentation.views.views.PresencialMenuView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PresencialMenuPresenter extends BasePresenter<PresencialMenuView> {
    private final FirebaseFirestore db;
    private long currentTime;
    private static final String[] mesesName = {
      "Enero","Febrero","Abril","Mayo","Junio","Julio","Agosto","Setiembre","Octubre","Noviembre","Diciembre"
    };

    private static final String TAG = "PresencialMenu";

    public PresencialMenuPresenter(PresencialMenuView view) {
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

    public void consultarMeses(){
        view.mostrarLoading();
        db.collection("eventos")
                .whereGreaterThanOrEqualTo("date",currentTime)
                .orderBy("date", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            procesarMeses(task);
                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                            view.ocultarLoading();
                        }
                    }
                });
    }

    private void procesarMeses(Task<QuerySnapshot> task){
        EventoDataMes dataMes = new EventoDataMes();
        List<String> meses = new ArrayList<>();
        List<EventoMes> eventoMesList = new ArrayList<>();
        int mesAnt = -1;
        List<EventoPresenc> eventoPresencList = new ArrayList<>();

        for (QueryDocumentSnapshot document : task.getResult()) {
            //Log.d(TAG, document.getId() + " => " + document.getData());
            Long date = document.getLong("date");
            if(date!=null){
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(date*1000);
                int m = calendar.get(Calendar.MONTH)-1;
                int y = calendar.get(Calendar.YEAR);
                if(mesAnt!=m){
                    meses.add(mesesName[m]+" "+y);
                    if(eventoPresencList.size()>0) {
                        EventoMes eventoMes = new EventoMes();
                        eventoMes.setEventos(eventoPresencList);
                        eventoMesList.add(eventoMes);

                        eventoPresencList = new ArrayList<>();
                    }
                    mesAnt = m;
                }

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
        }
        EventoMes eventoMes = new EventoMes();
        eventoMes.setEventos(eventoPresencList);
        eventoMesList.add(eventoMes);

        dataMes.setTituloMeses(meses);
        dataMes.setEventosPorMeses(eventoMesList);
        view.actualizarMeses(dataMes);
        view.ocultarLoading();
    }
}
