package com.goucorporation.android.orientateuni.presentation.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.goucorporation.android.orientateuni.R;
import com.goucorporation.android.orientateuni.presentation.models.EventoMes;
import com.goucorporation.android.orientateuni.presentation.models.EventoPresenc;
import com.goucorporation.android.orientateuni.presentation.utils.adapters.EventoPresenMescAdapter;

import java.util.List;

public class EventoPresencMesFragment extends Fragment implements EventoPresenMescAdapter.OnViewsClicked,
        ExpandableListView.OnGroupExpandListener{
    private static final String MES_TITULO = "MES_TITULO";
    private static final String MES_EVENTOS = "MES_EVENTOS";

    private String tituloMes;
    private EventoMes eventoMes;

    private ExpandableListView expList;
    private int lastGroup = -1;

    private TextView event_mes;
    private OnFragmentInteractionListener mListener;
    private EventoPresenMescAdapter adapter;

    public EventoPresencMesFragment() {
        // Required empty public constructor
    }

    public static EventoPresencMesFragment newInstance(String tituloMes, EventoMes eventoMes) {
        EventoPresencMesFragment fragment = new EventoPresencMesFragment();
        Bundle args = new Bundle();
        args.putString(MES_TITULO, tituloMes);
        args.putParcelable(MES_EVENTOS,eventoMes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tituloMes = getArguments().getString(MES_TITULO);
            eventoMes = getArguments().getParcelable(MES_EVENTOS);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_evento_presenc_mes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expList = view.findViewById(R.id.event_list);
        event_mes = view.findViewById(R.id.event_mes);
        expList.setOnGroupExpandListener(this);
        llenarLista();
    }

    private void llenarLista() {
        event_mes.setText(tituloMes);
        List<EventoPresenc> eventos = eventoMes.getEventos();
        adapter = new EventoPresenMescAdapter(getContext(),eventos,this);
        expList.setAdapter(adapter);
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
        mListener.onLinkClick(url);
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        if(lastGroup>=0 && lastGroup!= groupPosition)
            expList.collapseGroup(lastGroup); // Se cierra el grupo anterior
        lastGroup = groupPosition;
    }

    public interface OnFragmentInteractionListener {
        void onLinkClick(String url);
    }
}
