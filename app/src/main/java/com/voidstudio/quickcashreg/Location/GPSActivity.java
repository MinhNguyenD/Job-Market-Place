package com.voidstudio.quickcashreg.Location;

import android.Manifest;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.voidstudio.quickcashreg.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class GPSActivity extends AppCompatActivity implements View.OnClickListener {

  private static final int REQUEST_CODE_PERMISSION = 2;
  String fPermission = Manifest.permission.ACCESS_FINE_LOCATION;
  String cPermission = Manifest.permission.ACCESS_COARSE_LOCATION;
  private Location location;
  private String city;
  private GoogleMapsActivity googleMapsActivityFragment = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.gps_layout);

    GPS gps = new GPS(this);

    if(hasLocationAccessPermission()&&gps.canGetLocation()){
      location = gps.getLocation();
    }

    Button locationButton = (Button) findViewById(R.id.getLocation);
    locationButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (hasLocationAccessPermission()) {
          if (gps.canGetLocation()) {
            location = gps.getLocation();
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Toast.makeText(getBaseContext(), "Current location:(" + latitude + "," + longitude + ")",
                    Toast.LENGTH_LONG).show();
          }
        }
      }
    });

    Button googleMapsFragButton = (Button)findViewById(R.id.getGoogleMapsView);
    googleMapsFragButton.setOnClickListener(GPSActivity.this);

    Button calculateDistButton = (Button) findViewById(R.id.calculateDist);
    calculateDistButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        double distance = calculateDistance();
        Toast.makeText(getBaseContext(), "Distance: " + distance + " KM", Toast.LENGTH_LONG).show();
      }
    });
  }

  protected double[] getLongLat(String locationValue) {
    String[] parts = locationValue.split(",");
    double longitude = Double.parseDouble(parts[0]);
    double latitude = Double.parseDouble(parts[1]);
    return new double[]{longitude, latitude};
  }

  protected Location getSourceLocation() {
    EditText sourceLoc = findViewById(R.id.sourceLoc);
    String source = sourceLoc.getText().toString();
    double[] coord = getLongLat(source);
    Location location = new Location(LocationManager.GPS_PROVIDER);
    location.setLongitude(coord[0]);
    location.setLatitude(coord[1]);
    return location;
  }

  protected Location getDestLocation() {
    EditText destLoc = findViewById(R.id.destLoc);
    String destination = destLoc.getText().toString();
    double[] coord = getLongLat(destination);
    Location location = new Location(LocationManager.GPS_PROVIDER);
    location.setLongitude(coord[0]);
    location.setLatitude(coord[1]);
    return location;
  }

  protected double calculateDistance() {
    Location sourceLocation = getSourceLocation();
    Location destLocation = getDestLocation();
    return sourceLocation.distanceTo(destLocation) / 1000;
  }


  protected boolean hasLocationAccessPermission() {
    try {
      ActivityCompat.requestPermissions(this, new String[]{fPermission}, REQUEST_CODE_PERMISSION);
      ActivityCompat.requestPermissions(this, new String[]{cPermission}, REQUEST_CODE_PERMISSION);
      return true;
    } catch (Exception exc) {
      return false;
    }
  }

  private String cityFromLocationGetter(Location location){
    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    try {
      List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),
              1);
      city = addresses.get(0).getAddressLine(0);

    } catch (IOException e) {
      Toast.makeText(this,"BadLocation",Toast.LENGTH_SHORT).show();
    }
    return city;
  }

  public void onClick(View view){
    if(view.getId() == R.id.getGoogleMapsView){
      Intent intent = new Intent(this,GoogleMapsActivity.class);
      intent.putExtra("taskLocation", location);
      intent.putExtra("City", cityFromLocationGetter(location));
      startActivity(intent);
      Toast.makeText(getBaseContext(),"GoogleMapsFragment Added",
              Toast.LENGTH_SHORT).show();
    }
  }



}

