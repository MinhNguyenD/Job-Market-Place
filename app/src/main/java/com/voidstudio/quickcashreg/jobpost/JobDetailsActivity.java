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

  private static Firebase firebase;
  private static final String JOB_NAME = "job name";
  private static final String JOB_DURATION = "job duration";
  private static final String JOB_DATE_POSTED = "job date posted";
  private static final String JOB_TAG = "job tag";
  private static final String JOB_EMPLOYER = "job employer";
  private static final String JOB_WAGE = "job wage";

  private static String extractedJobName;
  private static String extractedWage;
  private static String extractedTag;
  private static String extractedDuration;
  private static String extractedDatePosted;
  private static String extractedJobEmployer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.job_details);
    firebase = Firebase.getInstance();
    Intent sentItem = getIntent();
    Bundle bundle = getIntent().getExtras();
    extractedJobName = bundle.getString(JOB_NAME);
    extractedWage = bundle.getString(JOB_WAGE);
    extractedTag = bundle.getString(JOB_TAG);
    extractedDuration = bundle.getString(JOB_DURATION);
    extractedDatePosted=bundle.getString(JOB_DATE_POSTED);
    extractedJobEmployer=bundle.getString(JOB_EMPLOYER);

    TextView jobName = findViewById(R.id.job_name_textView);
    jobName.setText(extractedJobName);
    TextView jobWage = findViewById(R.id.jobWage_textView);
    jobWage.setText(extractedWage);
    TextView jobDuration = findViewById(R.id.jobDuration_textView);
    jobDuration.setText(extractedDuration);
    TextView jobEmployer = findViewById(R.id.jobEmployer_textView);
    jobEmployer.setText(extractedJobEmployer);
    TextView jobDatePosted = findViewById(R.id.jobDate_textView);
    jobDatePosted.setText(extractedDatePosted);
    TextView jobTag = findViewById(R.id.jobTag_textView);
    jobTag.setText(extractedTag);
    Button applyButton = findViewById(R.id.applyJob);
    applyButton.setOnClickListener(JobDetailsActivity.this);

//    String itemLocation = getItemLocation(item);
//    String statusText = "Location: " + itemLocation;





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
