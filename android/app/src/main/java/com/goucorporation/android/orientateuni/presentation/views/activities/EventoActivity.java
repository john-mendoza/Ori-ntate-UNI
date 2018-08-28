/*
 *
 */

package com.goucorporation.android.orientateuni.presentation.views.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.goucorporation.android.orientateuni.presentation.views.fragments.EventoPresencFragment;
import com.goucorporation.android.orientateuni.presentation.views.fragments.EventoVirtualFragment;

public class EventoActivity extends AppCompatActivity implements EventoPresencFragment.OnFragmentInteractionListener{

    private int espc;
    private int tipoEvento;
    public static final String TIPO_ESPEC ="TIPO_ESPEC";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tipoEvento = getIntent().getIntExtra(EspecialidadActivity.TIPO_EVENTO,0);
        espc = getIntent().getIntExtra(TIPO_ESPEC,-1);

        setFragment();
    }

    private void setFragment(){
        Fragment fragment = null;
        switch (tipoEvento){
            case EspecialidadActivity.TIPO_PRESENC:
                fragment = EventoPresencFragment.newInstance(espc);
                break;
            case EspecialidadActivity.TIPO_VIRTUAL:
                fragment = EventoVirtualFragment.newInstance();
                break;
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(android.R.id.content, fragment);
            ft.commit();
        }
    }

    @Override
    public void onLinkClik(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
