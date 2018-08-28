/*
 *
 */

package com.goucorporation.android.orientateuni.presentation.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.goucorporation.android.orientateuni.R;
import com.goucorporation.android.orientateuni.presentation.views.fragments.EspecialidadesFragment;

public class EspecialidadActivity extends AppCompatActivity implements EspecialidadesFragment.OnFragmentInteractionListener{
    private int tipoEvento;
    public static final String TIPO_EVENTO = "TIPO_EVENTO";
    public static final int TIPO_PRESENC = 1;
    public static final int TIPO_VIRTUAL = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especialidad);
        tipoEvento = getIntent().getIntExtra(TIPO_EVENTO,0);
    }

    @Override
    public void goEventosEspec(int i) {
        Intent intent = new Intent(this,EventoActivity.class);
        intent.putExtra(TIPO_EVENTO,tipoEvento);
        intent.putExtra(EventoActivity.TIPO_ESPEC,i);
        startActivity(intent);
    }



}
