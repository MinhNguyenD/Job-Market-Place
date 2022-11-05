package com.voidstudio.quickcashreg.Location;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.voidstudio.quickcashreg.R;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Location latlong = null;
    String city = new String("Halifax");
    private static final int REQUEST_CODE_PERMISSION = 2;
    String fPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    String cPermission = Manifest.permission.ACCESS_COARSE_LOCATION;
    public int kilometerRadius = 1000;


    private GoogleMap mMap;
    private void initMap(){
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    /**
     * Used directly from android cookbook
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        GPS gps = new GPS(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if(getLocationPermission()){
         initMap();
         Location location = new Location("gps");
        }
        this.captureIntent();
    }

    protected boolean getLocationPermission(){
        try {
            ActivityCompat.requestPermissions(this, new String[]{fPermission}, REQUEST_CODE_PERMISSION);
            ActivityCompat.requestPermissions(this, new String[]{cPermission}, REQUEST_CODE_PERMISSION);
            return true;
        } catch (Exception exc) {
            return false;
        }
    }

    /**
     * Used directly from android cookbook
     */
    protected void captureIntent() {
        try {
            Intent latlongItent = getIntent();
            if (latlongItent != null) {
                Bundle bundle = latlongItent.getExtras();
                if (bundle.containsKey("taskLocation"))
                    this.latlong = (Location)bundle.get("taskLocation");
                if (bundle.containsKey("City"))
                    this.city = bundle.get("City").toString();
            }
            else{
                GPS gps = new GPS(this);
                latlong = gps.getLocation();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        GPS gps = new GPS(this);
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();


        LatLng itemLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(itemLocation).title(this.city));
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(itemLocation);
        circleOptions.radius(kilometerRadius*1000);
        circleOptions.strokeColor(Color.RED);
        mMap.addCircle(circleOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(itemLocation));

    }
}
