package com.voidstudio.quickcashreg.jobpost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.voidstudio.quickcashreg.Firebase;
import com.voidstudio.quickcashreg.InAppActivityEmployer;
import com.voidstudio.quickcashreg.R;
import com.voidstudio.quickcashreg.TextReader;

import users.Employer;

public class JobPostActivity extends AppCompatActivity implements View.OnClickListener {

  public static final String JOB_TAG_1 = "Tag1";
  public static final String JOB_TAG_2= "Tag2";
  public static final String JOB_TAG_3 = "Tag3";
  public static final String JOB_TAG_4 = "Tag4";
  public static final String JOB_TAG_5 = "Tag5";

  private static Firebase firebase;

  private Spinner jobTags;
  private String tag;
  private String job;
  private String wage;
  private String username;
  private String password;
  private String email;
  public static Employer employer;
  public static final String USERNAME = "Username";
  public static final String PASSWORD = "Password";
  private SharedPreferences sp;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.job_post);
    firebase = Firebase.getInstance();
    Intent thisIntent = getIntent();
    sp = getSharedPreferences("login", MODE_PRIVATE);
    username = sp.getString(USERNAME,"");
    password = sp.getString(PASSWORD,"");
    email = sp.getString("EMAIL","");
    employer = InAppActivityEmployer.employer;

    Button postButton = findViewById(R.id.postJobButton);
    postButton.setOnClickListener(JobPostActivity.this);

    Button myJobsButton = findViewById(R.id.myJobsButton);
    myJobsButton.setOnClickListener(JobPostActivity.this);

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

  private void postJob(String jobName, String jobWage, String jobTag){
    employer.setJob(jobName,jobWage,jobTag);
  }




  @Override
  public void onClick(View view){
    if(view.getId() == R.id.postJobButton) {
      postJob(getJobTitle(),getWage(),tag);
      Toast.makeText(JobPostActivity.this, "Posted", Toast.LENGTH_SHORT).show();
    }

    if(view.getId() == R.id.myJobsButton){
      Intent viewMyJobs = new Intent(JobPostActivity.this, EmployerJobBoardActivity.class);
      if(!getJobTitle().equals("")) {
        viewMyJobs.putExtra("Job", getJobTitle());
        viewMyJobs.putExtra("Wage", getWage());
        viewMyJobs.putExtra("Tag", tag);
      }
      startActivity(viewMyJobs);
    }

  }


}

