package com.goucorporation.android.orientateuni.presentation.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.goucorporation.android.orientateuni.R;
import com.goucorporation.android.orientateuni.presentation.models.EventoDataMes;
import com.goucorporation.android.orientateuni.presentation.views.fragments.PresencialFragment;

public class PresencialActivity extends AppCompatActivity implements PresencialFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presencial);
    }

    @Override
    public void goEventoEspecialidad() {
        Intent intent = new Intent(this,EspecialidadActivity.class);
        intent.putExtra(EspecialidadActivity.TIPO_EVENTO,EspecialidadActivity.TIPO_PRESENC);
        startActivity(intent);
    }

    @Override
    public void goEventoMes(int i, EventoDataMes dataMes) {
        Intent intent = new Intent(this,EventoPresencMesActivity.class);
        intent.putExtra(EventoPresencMesActivity.CURRENT_ITEM,i);
        intent.putExtra(EventoPresencMesActivity.DATA_MES,dataMes);
        startActivity(intent);
    }
}
