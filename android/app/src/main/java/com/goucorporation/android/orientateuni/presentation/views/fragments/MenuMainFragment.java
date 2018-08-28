/*
 *
 */

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

import com.goucorporation.android.orientateuni.R;
import com.goucorporation.android.orientateuni.presentation.utils.adapters.ListMenuAdapter;
import com.goucorporation.android.orientateuni.presentation.utils.adapters.ListMenuMapAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuMainFragment extends Fragment{
    private ExpandableListView expList1,expList2;

    private OnFragmentInteractionListener mListener;
    private ListMenuAdapter adapter1;
    private ListMenuMapAdapter adapter2;

    public MenuMainFragment() {
        // Required empty public constructor
    }

    public static MenuMainFragment newInstance() {
        return new MenuMainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expList1 = view.findViewById(R.id.expandList1);
        expList2 = view.findViewById(R.id.expandList2);
        expList1.setOnChildClickListener(listener1);
        expList2.setOnChildClickListener(listener2);
        iniciaLista();
    }

    private void iniciaLista(){
        List<String> titles1 = new ArrayList<>();
        List<String> titles2 = new ArrayList<>();
        List<List<String>> contents1 = new ArrayList<>();
        List<List<String>> contents2 = new ArrayList<>();
        titles1.add("Orientación\nVocacional");
        titles2.add("Guía UNI");
        contents1.add(Arrays.asList("Presencial","Virtual"));
        contents2.add(Arrays.asList("Facultades","Mapa"));
        adapter1 = new ListMenuAdapter(getContext(), titles1, contents1);
        adapter2 = new ListMenuMapAdapter(getContext(), titles2, contents2);
        expList1.setAdapter(adapter1);
        expList2.setAdapter(adapter2);
        expList1.expandGroup(0);
        expList2.expandGroup(0);

    }

    private ExpandableListView.OnChildClickListener listener1 = new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            if (childPosition == 0)
                mListener.goPresencial();
            else if (childPosition == 1)
                mListener.goVirtual();
            return false;
        }
    };

    private ExpandableListView.OnChildClickListener listener2 = new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            if (childPosition == 0)
                mListener.goCodigo();
            else if (childPosition == 1)
                mListener.goMapa();
            return false;
        }
    };

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

    public interface OnFragmentInteractionListener {
        void goPresencial();
        void goVirtual();
        void goCodigo();
        void goMapa();
    }
}
