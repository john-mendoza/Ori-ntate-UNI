/*
 *
 */

package com.goucorporation.android.orientateuni.presentation.views.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.goucorporation.android.orientateuni.R;
import com.goucorporation.android.orientateuni.presentation.views.fragments.MenuMainFragment;

public class MainActivity extends AppCompatActivity implements MenuMainFragment.OnFragmentInteractionListener{

    private static final int PERMISSION_REQUEST_FINE_LOCATION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void goPresencial() {
        Intent intent = new Intent(this,PresencialActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Make sure we have access coarse location enabled, if not, prompt the user to enable it
        if(Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
                        //Manifest.permission.ACCESS_COARSE_LOCATION};

                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Se necesita acceso a localización");
                builder.setMessage("Por favor, conceda el acceso de su ubicación para el trazado de rutas a lugares de la UNI.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(permissions, PERMISSION_REQUEST_FINE_LOCATION);
                    }
                });
                builder.show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_FINE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Localización de alta precisión concedida");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Funcionalidad limita");
                    builder.setMessage("Al no permitir el acceso a su ubicación, esta app no podrá trazar rutas hacia puntos de interés de la UNI");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }

    @Override
    public void goVirtual() {
        Intent intent = new Intent(this,EspecialidadActivity.class);
        intent.putExtra(EspecialidadActivity.TIPO_EVENTO,EspecialidadActivity.TIPO_VIRTUAL);
        startActivity(intent);
    }

    @Override
    public void goCodigo() {
        Intent intent = new Intent(this,CodigoActivity.class);
        startActivity(intent);
    }

    @Override
    public void goMapa() {
        Intent intent = new Intent(this,MapaActivity.class);
        startActivity(intent);
    }
}
