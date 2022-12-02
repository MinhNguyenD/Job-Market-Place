package com.voidstudio.quickcashreg.jobpost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.voidstudio.quickcashreg.EmploymentHistoryActivity;
import com.voidstudio.quickcashreg.R;
import com.voidstudio.quickcashreg.firebase.Firebase;

public class JobDetailsActivity extends AppCompatActivity implements View.OnClickListener {

  private static final String JOB_NAME = "job";
  private String jobNameItem;
  private static Firebase firebase;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.job_details);
    firebase = Firebase.getInstance();
    Intent sentItem = getIntent();
    jobNameItem = sentItem.getStringExtra("selectedItem");
    TextView jobName = findViewById(R.id.jobName);
    Button applyButton = findViewById(R.id.applyJob);
    applyButton.setOnClickListener(JobDetailsActivity.this);

//    String itemLocation = getItemLocation(item);
//    String statusText = "Location: " + itemLocation;
    jobName.setText(jobNameItem);

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

//  protected String getLocationCoordinate(String itemLocation) {
//    SharedPreferences sharedPreferences = getSharedPreferences(EmployerJobBoardActivity.MY_PREFS, Context.MODE_PRIVATE);
//    return sharedPreferences.getString(itemLocation, null);
//  }
//
//  protected String getItemLocation(String item) {
//    SharedPreferences sharedPreferences = getSharedPreferences(EmployerJobBoardActivity.MY_PREFS, Context.MODE_PRIVATE);
//    return sharedPreferences.getString(item, null);
//  }

  public void onClick(View view) {
    if(view.getId() == R.id.applyJob){
      Toast.makeText(this, "The application is accepted", Toast.LENGTH_LONG).show();
      //TODO: Implement the application process
      Intent employment = new Intent(JobDetailsActivity.this, EmploymentHistoryActivity.class);
      startActivity(employment);
    }
  }

}
