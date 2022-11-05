package com.voidstudio.quickcashreg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.SharedPreferences;
import users.Employee;

import androidx.appcompat.app.AppCompatActivity;

public class InAppActivityEmployee extends AppCompatActivity implements View.OnClickListener {
  private SharedPreferences sp;
  private Firebase firebase;
  public static Employee employee;
  private static String username;
  private static String password;
  private static String email;
  private static String userType;
  public static final String USERNAME = "Username";
  public static final String PASSWORD = "Password";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.in_app_activity_employee);
    firebase= Firebase.getInstance();
    // switch to this activity when log in successfully
    Intent thisIntent = getIntent();
    String welcomeMessage = thisIntent.getStringExtra(LogInActivity.WELCOME);
    TextView message = findViewById(R.id.Employee);
    message.setText(welcomeMessage);

    sp = getSharedPreferences("login", MODE_PRIVATE);
    username = sp.getString(USERNAME,"");
    password = sp.getString(PASSWORD,"");
    email = firebase.getEmailAddress(username);

    userType =  firebase.getUserType(username);
    //TODO: REFACTOR SO THAT SHARED PREF CAN STORE USERTYPE
    employee = new Employee(username,email,userType,password);

    Button logOutButton = findViewById(R.id.employeeLogOut);
    logOutButton.setOnClickListener(InAppActivityEmployee.this);

    Button userProfileButton = findViewById(R.id.user_profile);
    userProfileButton.setOnClickListener(InAppActivityEmployee.this);


    Button jobPostingButton = findViewById(R.id.job_posting);
    jobPostingButton.setOnClickListener(InAppActivityEmployee.this);


    Button userApplicationButton = findViewById(R.id.user_application);
    userApplicationButton.setOnClickListener(InAppActivityEmployee.this);
  }

  //This method could be in its own class, many different activities will need it
  private void resetLogInStatus() {
    SharedPreferences sharedPrefs = getSharedPreferences(LogInActivity.PREFERENCES, Context.MODE_PRIVATE); //
    SharedPreferences.Editor editor = sharedPrefs.edit();
    editor.putBoolean(LogInActivity.ISLOGGED, false);
    editor.commit();
  }

  @Override
  public void onClick(View view){
    if(view.getId() == R.id.employeeLogOut){
      resetLogInStatus();
      Intent logOutIntent = new Intent(InAppActivityEmployee.this, LogInActivity.class);
      startActivity(logOutIntent);
    }
    else if(view.getId() == R.id.user_profile){
      Intent userProfileIntent = new Intent(InAppActivityEmployee.this, UserProfileActivity.class);
      userProfileIntent.putExtra(USERNAME, username);
      userProfileIntent.putExtra(PASSWORD, password);
//      userProfileIntent.putExtra("EMAIL",email);
      startActivity(userProfileIntent);
    }
    else if(view.getId() == R.id.job_posting){
      Intent jobPostingIntent = new Intent(InAppActivityEmployee.this, JobPostingActivity.class);
      startActivity(jobPostingIntent);
    }
    else if(view.getId() == R.id.user_application){
      Intent userApplicationIntent = new Intent(InAppActivityEmployee.this, UserApplicationActivity.class);
      startActivity(userApplicationIntent);
    }
  }

}
