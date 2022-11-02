package com.voidstudio.quickcashreg.jobpost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.voidstudio.quickcashreg.R;
import com.voidstudio.quickcashreg.TextReader;

public class JobPostActivity extends AppCompatActivity implements View.OnClickListener {

  public static final String JOB_TAG_1 = "Same Day";
  public static final String JOB_TAG_2= "One time Job";
  public static final String JOB_TAG_3 = "Multiple tasks";
  public static final String JOB_TAG_4 = "Active";
  public static final String JOB_TAG_5 = "Sedentary";

  private Spinner jobTags;
  private String tag;
  private String job;
  private String wage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.job_post);


    Button postButton = findViewById(R.id.postJobButton);
    postButton.setOnClickListener(JobPostActivity.this);

    setUpJobTagSpinner();
    jobTagsSpinnerListener();
  }

  private void jobTagsSpinnerListener() {
    jobTags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int pos, long l) {
        tag = adapterView.getItemAtPosition(pos).toString();
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });
  }

  private void setUpJobTagSpinner() {
    jobTags = findViewById(R.id.jobTagsSpinner);
    String[] roles = new String[]{JOB_TAG_1,JOB_TAG_2,JOB_TAG_3,JOB_TAG_4,JOB_TAG_5};
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roles);
    jobTags.setAdapter(adapter);
  }

  private String getJobTitle(){
    EditText jobTitle = findViewById(R.id.jobTitle);
    TextReader tr = new TextReader();
    return tr.getFromEditText(jobTitle);
  }

  private String getWage(){
    EditText wage = findViewById(R.id.wageEdit);
    TextReader tr = new TextReader();
    return tr.getFromEditText(wage);
  }




  @Override
  public void onClick(View view){
    if(view.getId() == R.id.postJobButton) {
      Toast.makeText(JobPostActivity.this, "Posted", Toast.LENGTH_SHORT).show();
      Intent postedSwitch = new Intent(JobPostActivity.this, JobBoardActivity.class);
      postedSwitch.putExtra("Job", getJobTitle());
      postedSwitch.putExtra("Wage", getWage());
      postedSwitch.putExtra("Tag", tag);
      startActivity(postedSwitch);
    }

  }


}
