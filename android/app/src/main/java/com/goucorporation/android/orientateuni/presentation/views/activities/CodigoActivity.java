/*
 *
 */

package com.goucorporation.android.orientateuni.presentation.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.goucorporation.android.orientateuni.R;
import com.goucorporation.android.orientateuni.presentation.views.fragments.CodigosFragment;

public class CodigoActivity extends AppCompatActivity implements CodigosFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo);
    }


    @Override
    public void onCodigoClicked(int i) {
        Intent intent = new Intent(this,MapaActivity.class);
        intent.putExtra(MapaActivity.POSIC,i);
        startActivity(intent);
    }
}
