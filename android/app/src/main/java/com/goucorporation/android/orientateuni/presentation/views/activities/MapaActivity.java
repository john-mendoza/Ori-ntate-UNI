/*
 *
 */

package com.goucorporation.android.orientateuni.presentation.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.goucorporation.android.orientateuni.R;
import com.goucorporation.android.orientateuni.presentation.views.fragments.MapaFragment;

public class MapaActivity extends AppCompatActivity {


    public static final String POSIC = "POSIC";
    private static final String TAG = "MAPA_FRAGMENT";

    private int mPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        mPos = getIntent().getIntExtra(POSIC,-1);

        connectGoogleApiClient();

        setFragment();
    }

    private void connectGoogleApiClient(){
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();
    }

    private void setFragment(){
        Fragment fragment = MapaFragment.newInstance(mPos);
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(android.R.id.content, fragment,TAG);
            ft.commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG);
        fragment.onActivityResult(requestCode,resultCode,data);
    }
}
