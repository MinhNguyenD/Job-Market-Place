package com.voidstudio.quickcashreg.jobpost;

import static com.voidstudio.quickcashreg.jobpost.EmployerJobBoardActivity.MY_PREFS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.voidstudio.quickcashreg.R;

public class JobDetailsActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.job_details);

    Intent sentItem = getIntent();
    String item = sentItem.getStringExtra("selectedItem");
    TextView statusLabel = findViewById(R.id.statusLabel);
    Job job = Job.getFromString(item);
    if(job == null){
      job = Job.getFromString(item);
    }
    job.getLocation();
    //String itemLocation = sentItem.getStringExtra("Location");
    String statusText = "Location: " + job.getLocation().getLongitude()+" "+job.getLocation().getLongitude();
    statusLabel.setText(statusText);

    //  ImageButton mapButton = findViewById(R.id.mapButton);
    //  mapButton.setOnClickListener(new View.OnClickListener() {
//
    //    @Override
    //    public void onClick(View view) {
    //      String locCoord = getLocationCoordinate(itemLocation);
    //      Intent mapIntent = new Intent(getBaseContext(), GoogleMapsActivity.class);
    //      mapIntent.putExtra("city", itemLocation);
    //      mapIntent.putExtra("taskLocation", locCoord);
    //      startActivity(mapIntent);
    //    }
    //  });
  }

  protected String getLocationCoordinate(String itemLocation) {
    SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
    return sharedPreferences.getString(itemLocation, null);
  }
  protected String getItemLocation() {
    SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
    return sharedPreferences.getString("Location", null);
  }
}
