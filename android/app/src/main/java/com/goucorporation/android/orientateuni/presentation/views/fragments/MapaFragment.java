/*
 *
 */

package com.goucorporation.android.orientateuni.presentation.views.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.TravelMode;
import com.goucorporation.android.orientateuni.R;
import com.goucorporation.android.orientateuni.presentation.models.Lugar;
import com.goucorporation.android.orientateuni.presentation.utils.data.LugarData;

import java.util.ArrayList;
import java.util.List;

public class MapaFragment extends Fragment implements View.OnClickListener,
        GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private int mPos;
    public static final String POSIC = "POSIC";

    private static final String TAG = "REQUEST_LOCATION";
    private static final int REQUEST_CHECK_SETTINGS = 4321;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private List<Marker> marcadores;
    private Marker currentMarker;
    private Boolean mLocationPermissionsGranted = false;
    private MapView map_locat;
    private GoogleMap mGoogleMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LatLng currentLocation;
    private LatLng destino;
    private Polyline polyline;
    private Handler mHandler;
    private LocationManager mLocationManager;

    private static final float DEFAULT_ZOOM_MARK = 18f;
    private static final float DEFAULT_ZOOM_UNI = 16f;
    private static final LatLng uniLatLng = new LatLng(-12.019889, -77.049417);

    private LocationRequest mLocationRequest;
    private ImageView img_lugar;

    //private OnFragmentInteractionListener mListener;

    public MapaFragment() {
        // Required empty public constructor
    }

    public static MapaFragment newInstance(int pos) {
        MapaFragment fragment = new MapaFragment();
        Bundle args = new Bundle();
        args.putInt(POSIC, pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPos = getArguments().getInt(POSIC);
        }
        mHandler = new Handler();
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);

        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mapa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        map_locat = view.findViewById(R.id.map_locat);
        FloatingActionButton fab_go = view.findViewById(R.id.fab_go);
        FloatingActionButton fab_legend = view.findViewById(R.id.fab_legend);
        img_lugar = view.findViewById(R.id.img_lugar);

        fab_go.setOnClickListener(this);
        fab_legend.setOnClickListener(this);
        img_lugar.setOnClickListener(this);

        map_locat.onCreate(savedInstanceState);

        getLocationPermission();

    }

    @Override
    public void onStart() {
        super.onStart();
        map_locat.onStart();
        displayLocationSettingsRequest(getContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        map_locat.onStart();
        stopLocationUpdates();
    }

    @Override
    public void onResume() {
        super.onResume();
        map_locat.onResume();
    }

    @Override
    public void onStop() {
        map_locat.onStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        map_locat.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        map_locat.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        map_locat.onLowMemory();
        super.onLowMemory();
    }
/*
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
    }*/

    /*
    private GnssStatus.Callback mGnssCallback = new GnssStatus.Callback() {
        @Override
        public void onStarted() {
            super.onStarted();
            startLocationUpdates();
        }

        @Override
        public void onStopped() {
            super.onStopped();
            stopLocationUpdates();
        }
    };

    private GpsStatus.Listener mGpsListener = new GpsStatus.Listener() {
        @Override
        public void onGpsStatusChanged(int event) {
            switch (event){
                case GpsStatus.GPS_EVENT_STARTED:
                    startLocationUpdates();
                    break;
                case GpsStatus.GPS_EVENT_STOPPED:
                    stopLocationUpdates();
                    break;
            }
        }
    };

    private void stopGpsStatusCallback(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mLocationManager.unregisterGnssStatusCallback(mGnssCallback);
        }else{
            mLocationManager.removeGpsStatusListener(mGpsListener);
        }
    }

    private void startGpsStatusCallback() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mLocationManager.registerGnssStatusCallback(mGnssCallback);
        }else {
            mLocationManager.addGpsStatusListener(mGpsListener);
        }
    }*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        //mGoogleMap.setTrafficEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

        agregarMarcadores();
        mGoogleMap.setOnMarkerClickListener(this);


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);

    }

    private void agregarMarcadores() {
        marcadores = new ArrayList<>();


        for (Lugar lugar : LugarData.lugares) {
            float color = 0;
            switch (lugar.getTipo()) {
                case 1:
                    color = 207f;
                    break;
                case 2:
                    color = 343f;
                    break;
                case 3:
                    color = 131f;
                    break;
            }

            marcadores.add(mGoogleMap.addMarker(new MarkerOptions()
                    .position(lugar.getLatLng())
                    .icon(BitmapDescriptorFactory.defaultMarker(color))
                    .title(lugar.getNombre())));
        }

        if (mPos >= 0) {
            Marker marcador = marcadores.get(mPos);

            if (marcador != null) {
                destino = marcador.getPosition();
                moveCamera(destino, DEFAULT_ZOOM_MARK);
                marcador.showInfoWindow();
            } else {
                moveCamera(uniLatLng, DEFAULT_ZOOM_UNI);
            }
        } else {
            moveCamera(uniLatLng, DEFAULT_ZOOM_UNI);
        }
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            Location location = locationResult.getLastLocation();
            if (location != null) {
                if(currentMarker!=null)
                    currentMarker.remove();
                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                currentMarker = mGoogleMap.addMarker(new MarkerOptions()
                        .position(currentLocation)
                        .title("Mi ubicación"));
            }
        }
    };

    private void stopLocationUpdates() {
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    private void getCurrentLocation() {
        Log.d("UBICAC", "getDeviceLocation: getting the devices current location");

        try{
            if(mLocationPermissionsGranted){
                final Task<Location> location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if(task.isSuccessful()) {
                            Location location = task.getResult();
                            if (location!=null) {
                                if(currentMarker!=null)
                                    currentMarker.remove();
                                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                currentMarker = mGoogleMap.addMarker(new MarkerOptions()
                                        .position(currentLocation)
                                        .title("Mi ubicación"));
                            }
                        }else{
                            Toast.makeText(MapaFragment.this.getContext(),
                                    "No se puede encontrar su ubicación actual",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e("UBICAC","getCurrentLocation: "+e.getMessage());
        }
    }

    private void getLocationPermission() {
        Log.d("UBICAC", "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
               // Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
            //        COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            //}else{
            //    ActivityCompat.requestPermissions(getActivity(),
            //            permissions,
            //            LOCATION_PERMISSION_REQUEST_CODE);
            //}
        }else{
            ActivityCompat.requestPermissions(getActivity(),
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void initMap() {
        map_locat.getMapAsync(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d("UBICAC", "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d("UBICAC", "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    initMap();
                }
            }
        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void moveCamera(LatLng pos1,LatLng pos2){
        int width = getResources().getDisplayMetrics().widthPixels;
        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(pos1);
        builder.include(pos2);
        LatLngBounds bounds = builder.build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,padding));
    }

    private void trazarRuta(LatLng pos1,LatLng pos2){
        if(pos1!=null && pos2!=null) {
            String origin = pos1.latitude + "," + pos1.longitude;
            String destination = pos2.latitude + "," + pos2.longitude;

            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.google_maps_key))
                    .build();

            DirectionsApiRequest req = DirectionsApi.getDirections(context, origin, destination).mode(TravelMode.WALKING);
            req.setCallback(resCallback);
        }
    }

    private void displayLocationSettingsRequest(final Context context) {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        final Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(context).checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // todos los requerimientos completos, se procede a obtener la ubicacion
                    getCurrentLocation();
                    startLocationUpdates();
                } catch (ApiException e) {
                    switch (e.getStatusCode()){
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) e;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                        MapaFragment.this.getActivity(),
                                        REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e1) {
                                //e1.printStackTrace();
                            } catch (ClassCastException e2){
                                // ignore
                            }
                        break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }

            }
        });
    }

    private PendingResult.Callback<DirectionsResult> resCallback = new PendingResult.Callback<DirectionsResult>() {
        @Override
        public void onResult(DirectionsResult result) {
            //Define list to get all latlng for the route
            List<LatLng> path = new ArrayList();


            if(result.routes != null && result.routes.length > 0){
                DirectionsRoute route = result.routes[0];

                if (route.legs !=null) {
                    for(int i=0; i<route.legs.length; i++) {
                        DirectionsLeg leg = route.legs[i];
                        if (leg.steps != null) {
                            for (int j=0; j<leg.steps.length;j++){
                                DirectionsStep step = leg.steps[j];
                                if (step.steps != null && step.steps.length >0) {
                                    for (int k=0; k<step.steps.length;k++){
                                        DirectionsStep step1 = step.steps[k];
                                        EncodedPolyline points1 = step1.polyline;
                                        if (points1 != null) {
                                            //Decode polyline and add points to list of route coordinates
                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                path.add(new LatLng(coord1.lat, coord1.lng));
                                            }
                                        }
                                    }
                                } else {
                                    EncodedPolyline points = step.polyline;
                                    if (points != null) {
                                        //Decode polyline and add points to list of route coordinates
                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                        for (com.google.maps.model.LatLng coord : coords) {
                                            path.add(new LatLng(coord.lat, coord.lng));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }

            if(path.size() > 0){
                final PolylineOptions opts = new PolylineOptions().addAll(path)
                        .color(Color.parseColor("#2196f3"))
                        .width(10);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(polyline!=null)
                            polyline.remove();
                        polyline = mGoogleMap.addPolyline(opts);
                        moveCamera(currentLocation,destino);
                    }
                });
            }
        }

        @Override
        public void onFailure(final Throwable e) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MapaFragment.this.getActivity(),"Error inesperado: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_lugar:
                ocultarImagen();
                break;
            case R.id.fab_go:
                ir();
                break;
            case R.id.fab_legend:
                mostrarLeyenda();
                break;
        }
    }

    private void mostrarLeyenda(){
        DialogFragment dialog = new LegendDialogFragment();
        dialog.show(getFragmentManager(),"LEGEND");
    }

    private void ir(){
        if(currentLocation!=null && destino!=null){
            trazarRuta(currentLocation,destino);
        }else{
            Toast.makeText(getActivity(),"Puntos de origen y destino faltantes.",Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarMapa(){
        if(polyline!=null)
            polyline.remove();
        moveCamera(uniLatLng,DEFAULT_ZOOM_UNI);
    }

    private void ocultarImagen(){
        img_lugar.setVisibility(View.GONE);
    }

    private void mostrarImagen(int pos){
        Lugar lugar = LugarData.lugares.get(pos);
        Glide.with(this).load(lugar.getResId()).into(img_lugar);
        img_lugar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(!marker.equals(currentMarker)) {
            destino = marker.getPosition();
            int pos = marcadores.indexOf(marker);
            mostrarImagen(pos);
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        //Toast.makeText(getContext(),"aceptado", Toast.LENGTH_SHORT).show();
                        getCurrentLocation();
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        //Toast.makeText(getContext(),"Cancelado", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        //Toast.makeText(getContext(),"Otro resultado", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            default:
                //Toast.makeText(getContext(),"Otro requestCode: "+requestCode, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /*
    public interface OnFragmentInteractionListener {

    }*/
}
