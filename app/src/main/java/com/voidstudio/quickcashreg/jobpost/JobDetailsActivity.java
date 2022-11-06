package com.voidstudio.quickcashreg.jobpost;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.voidstudio.quickcashreg.Location.GoogleMapsActivity;
import com.voidstudio.quickcashreg.R;

public class JobDetailsActivity extends AppCompatActivity {

  private static final int REQUEST_CODE_PERMISSION = 2;
  String fPermission = Manifest.permission.ACCESS_FINE_LOCATION;
  String cPermission = Manifest.permission.ACCESS_COARSE_LOCATION;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.job_details);

    Intent sentItem = getIntent();
    String item = sentItem.getStringExtra("selectedItem");
    String distanceToYou = sentItem.getStringExtra("Distance to you");
    TextView statusLabel = findViewById(R.id.statusLabel);
    String itemLocation = getItemLocation(item);

    String statusText = "Location: " + itemLocation + "\nDistance to you: " + distanceToYou;
    statusLabel.setText(statusText);

    try {
      ActivityCompat.requestPermissions(this, new String[]{fPermission}, REQUEST_CODE_PERMISSION);
      ActivityCompat.requestPermissions(this, new String[]{cPermission}, REQUEST_CODE_PERMISSION);
    } catch (Exception exc) {
      Toast.makeText(this,"Enable Location Permission",Toast.LENGTH_LONG).show();
    }
    ImageButton mapButton = findViewById(R.id.mapButton);
    mapButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        String locCoord = getLocationCoordinate(itemLocation);
        Intent mapIntent = new Intent(getBaseContext(), GoogleMapsActivity.class);
        mapIntent.putExtra("City", itemLocation);
        mapIntent.putExtra("taskLocation", locCoord);
        startActivity(mapIntent);
      }
    });
  }

  protected String getLocationCoordinate(String itemLocation) {
    SharedPreferences sharedPreferences = getSharedPreferences(EmployerJobBoardActivity.MY_PREFS, Context.MODE_PRIVATE);
    return sharedPreferences.getString(itemLocation, null);
  }

  protected String getItemLocation(String item) {
    SharedPreferences sharedPreferences = getSharedPreferences(EmployerJobBoardActivity.MY_PREFS, Context.MODE_PRIVATE);
    return sharedPreferences.getString(item, null);
  }
}

