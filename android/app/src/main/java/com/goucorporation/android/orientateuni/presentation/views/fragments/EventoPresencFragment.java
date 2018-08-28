package com.goucorporation.android.orientateuni.presentation.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bluejamesbond.text.DocumentView;
import com.goucorporation.android.orientateuni.R;
import com.goucorporation.android.orientateuni.presentation.models.Especialidad;
import com.goucorporation.android.orientateuni.presentation.models.EventoMes;
import com.goucorporation.android.orientateuni.presentation.models.EventoPresenc;
import com.goucorporation.android.orientateuni.presentation.presenters.EventoPresencPresenter;
import com.goucorporation.android.orientateuni.presentation.utils.adapters.EventoPresenMescAdapter;
import com.goucorporation.android.orientateuni.presentation.utils.data.EspecData;
import com.goucorporation.android.orientateuni.presentation.views.views.EventoPresencView;

import java.util.List;

public class EventoPresencFragment extends Fragment implements EventoPresenMescAdapter.OnViewsClicked,
        ExpandableListView.OnGroupExpandListener, EventoPresencView{
    private OnFragmentInteractionListener mListener;
    private int espc = -1;
    public static final String ESPEC = "ESPEC";
    private static final String EVENTOS = "EVENTOS";

    private TextView espc_title;
    private DocumentView espc_descr;
    private ExpandableListView expList;
    private int lastGroup = -1;
    private Button espc_link;
    private EventoPresenMescAdapter adapter;
    private ProgressBar progressBar;
    private EventoMes eventoMes;
    private Especialidad especialidad;
    private EventoPresencPresenter presenter;
    public EventoPresencFragment() {
        // Required empty public constructor
    }

    public static EventoPresencFragment newInstance(int espec) {
        EventoPresencFragment fragment = new EventoPresencFragment();
        Bundle args = new Bundle();
        args.putInt(ESPEC,espec);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            espc = getArguments().getInt(ESPEC);
            especialidad = EspecData.especialidades.get(espc);
        }
        if(savedInstanceState!=null){
            eventoMes = savedInstanceState.getParcelable(EVENTOS);
        }
        presenter = new EventoPresencPresenter(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(EVENTOS,eventoMes);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_evento_presenc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        espc_title = view.findViewById(R.id.espec_titulo);
        espc_descr = view.findViewById(R.id.espec_descr);
        expList = view.findViewById(R.id.event_list);
        expList.setOnGroupExpandListener(this);
        espc_link = view.findViewById(R.id.bt_especialidad);
        progressBar = view.findViewById(R.id.progressbar);
    }

    @Override
    public void onStart() {
        super.onStart();
        descrip();
        if(eventoMes==null){
            presenter.consultarEventos(especialidad.getFacultad());
        }else{
            llenarDatos(eventoMes);
        }
    }

    private void descrip(){
        if(espc>=0){
            espc_title.setText(especialidad.getNombre());
            espc_descr.setText(especialidad.getDescr());
            espc_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onLinkClik(especialidad.getLink());
                }
            });
        }
    }

    private void llenarDatos(EventoMes eventoMes) {
        if(espc>=0){
            List<EventoPresenc> eventos = eventoMes.getEventos();
            adapter = new EventoPresenMescAdapter(getContext(),eventos,this);
            expList.setAdapter(adapter);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLinkClicked(String url) {
        mListener.onLinkClik(url);
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        if(lastGroup>=0 && lastGroup!= groupPosition)
            expList.collapseGroup(lastGroup); // Se cierra el grupo anterior
        lastGroup = groupPosition;
    }

    @Override
    public void mostrarEventos(EventoMes eventos) {
        llenarDatos(eventos);
        this.eventoMes = eventos;
    }

    @Override
    public void mostrarLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void ocultarLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void mostrarMensaje(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    public interface OnFragmentInteractionListener {
        void onLinkClik(String url);
    }
}
