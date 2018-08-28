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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.goucorporation.android.orientateuni.R;
import com.goucorporation.android.orientateuni.presentation.models.EventoDataMes;
import com.goucorporation.android.orientateuni.presentation.presenters.PresencialMenuPresenter;
import com.goucorporation.android.orientateuni.presentation.utils.adapters.MesListAdapter;
import com.goucorporation.android.orientateuni.presentation.views.views.PresencialMenuView;

import java.util.ArrayList;
import java.util.List;

public class PresencialFragment extends Fragment implements ExpandableListView.OnChildClickListener,
        View.OnClickListener, PresencialMenuView{
    private static final String DATA_EVENTOS = "DATA_EVENTOS";

    private ExpandableListView expList;

    private OnFragmentInteractionListener mListener;

    private MesListAdapter adapter;

    private LinearLayout progressbar;

    private PresencialMenuPresenter presenter;

    private EventoDataMes dataMes;
    private List<String> meses;

    public PresencialFragment() {

    }

    public static PresencialFragment newInstance() {
        return new PresencialFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null){
            dataMes = savedInstanceState.getParcelable(DATA_EVENTOS);
        }

        presenter = new PresencialMenuPresenter(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(DATA_EVENTOS,dataMes);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_presencial, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn_espec = view.findViewById(R.id.bt_especialidad);
        progressbar = view.findViewById(R.id.progressbar);
        expList = view.findViewById(R.id.expandList);
        expList.setOnChildClickListener(this);
        btn_espec.setOnClickListener(this);
        //iniciaLista();
    }

    private void iniciaLista(List<String> meses){
        List<String> titles = new ArrayList<>();
        List<List<String>> contents = new ArrayList<>();
        titles.add("Eventos");
        contents.add(meses);
        adapter = new MesListAdapter(getContext(), titles, contents);
        expList.setAdapter(adapter);
        expList.expandGroup(0);
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
    public void onStart() {
        super.onStart();
        if(dataMes==null) {
            presenter.consultarMeses();
        }else {
            actualizarMeses(dataMes);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        if (groupPosition==0){
            mListener.goEventoMes(childPosition,dataMes);
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bt_especialidad)
            mListener.goEventoEspecialidad();
    }

    @Override
    public void actualizarMeses(EventoDataMes dataMes) {
        iniciaLista(dataMes.getTituloMeses());
        this.dataMes = dataMes;
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void mostrarMensaje(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarLoading() {
        expList.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void ocultarLoading() {
        expList.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.GONE);
    }

    public interface OnFragmentInteractionListener {
        void goEventoEspecialidad();
        void goEventoMes(int i, EventoDataMes dataMes);
    }
}
